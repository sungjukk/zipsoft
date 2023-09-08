import axios, {AxiosRequestConfig, AxiosProgressEvent, ResponseType, AxiosResponse, AxiosError} from "axios";
import store from '@/store/index';
import { globals } from "@/main";

export enum HTTP_STATUS {
	OK = 200,
	CREATED = 201,
	ACCEPTED = 202,
	NO_CONTENT = 204,
	BAD_REQUEST = 400,
	UNAUTHORIZED = 401,
	SERVER_ERROR = 500
}

interface ApiResponse {
	result : Number,
	msg? : String,
	data? : any
}

const API_URL = process.env.VUE_APP_API_URL;

const instance = axios.create({
	baseURL: API_URL,
	withCredentials: true,
	headers: {
		'Content-Type' : 'application/json'
	}
});


instance.interceptors.request.use(config => {
	
	const accessToken = sessionStorage.getItem('authorization');
	//config.headers['Content-Type'] = 'application/json';
	if (accessToken) config.headers['Authorization'] = `Bearer ${accessToken}`;
    return config;
}, err => {
	return Promise.reject(err);
});

instance.interceptors.response.use(response => response, async (err) => {
	const { config, response } = err;
	if (response && response.status == HTTP_STATUS.UNAUTHORIZED) {

		const {data} = response;

		if (data) {
			let responseData = {} as any;
			
			if (data instanceof Blob) {
				 responseData = JSON.parse(await data.text());
			} else {
				responseData = data;
			}

			// 401에러 && 토큰 만료인 경우 새로 받아옴
			if (responseData.msg === 'expired') {
				const res = await republicToken();
				if (res) {
					console.log('재인증');
					config.headers['Authorization'] = `Bearer ${sessionStorage.getItem('authorization')}`;
					return axios(config);
				} else {
					console.log('실패');
					await store.dispatch('UserStore/logout');
				} 
			}

		}
	} 

	return Promise.reject(err);
	
})

export const callGetApi = async(url : string, params? : object) => {
	const encUrl = encodeURI(url);
	
	try {
		const result : AxiosResponse<any> = await instance.get(encUrl + appendGetParams(params));
		return returnData(result.data);	
	} catch (err) {
		if (axios.isAxiosError(err)) {
			// axios에서 발생한 error
			return returnData(err.response?.data);
		} else {
			return returnData(null);
		}
	}
	
	
};

export const callPostApi = async(url:string, params?:object) => {
	const encUrl = encodeURI(url);
	try {
		const result : AxiosResponse<any> = await instance.post(encUrl, params);

		console.log(result);
		return returnData(result.data);		
	} catch (err) {
		if (axios.isAxiosError(err)) {
			return returnData(err.response?.data);			
		} else {
			return returnData(null);
		}
	}
	
	
};

export const callDeleteApi = async(url:string, params?:object) => {
	const encUrl = encodeURI(url);
	try {
		const result : AxiosResponse<any> = await instance.delete(encUrl, params);

		console.log(result);
		return returnData(result.data);		
	} catch (err) {
		if (axios.isAxiosError(err)) {
			return returnData(err.response?.data);			
		} else {
			return returnData(null);
		}
	}
	
	
};

export const callFileApi = async(url:string, params?:object) => {
	const config = {
		onUploadProgress: (event : any) => {
			const per = Math.round((event.loaded * 100) / event.total);
			globals.$loadingBar(true, per);
		},
		headers : {
			"Content-Type": "multipart/form-data"
		}
	};

	try {
		const result : AxiosResponse<any> = await instance.post(url, params, config);
		return returnData(result.data);		
	} catch (err) {
		if (axios.isAxiosError(err)) {
			return returnData(err.response?.data);			
		} else {
			return returnData(null);
		} 
	} finally {
		globals.$loadingBar(false, 0);
	}
};

export const callFileDownApi = async(url:string, params?:object) => {
	const config = {
		onDownloadProgress: (event : any) => {
			const per = Math.round((event.loaded * 100) / event.total);
			globals.$loadingBar(true, per);
		},
		responseType : 'blob' as ResponseType
	}

	try {
		const result : AxiosResponse<any> = await instance.get(url, config);
		const fileName = result.headers['content-disposition'].split('filename=')[1].replaceAll(/"/gi,'');

		const fileUrl = window.URL.createObjectURL(new Blob([result.data], {
			type: result.headers['content-type'],
		  }));
		const link = document.createElement('a');
		link.href = fileUrl;
		link.download = decodeURI(fileName);
		document.body.appendChild(link);
		link.click();

	} catch (err) {
		globals.$alert('다운로드에 실패하였습니다');
		return false;
	} finally {
		globals.$loadingBar(false, 0);
	}
}
	

const appendGetParams = (params?:any) => {
  if (!params) return '';

  let paramSuffix = '';

  let loop = 0;
  for (const paramKey in params) {
    const symbol = loop === 0 ? '?' : '&';
    paramSuffix += symbol + paramKey + '=' + params[paramKey];
    loop++;
  }

  return paramSuffix;
};


const republicToken =  async () => {
	
	try {
		const res:AxiosResponse<any> = await instance.get('/auth/republishToken');
		const data : ApiResponse = res.data;
		console.log('republicToken',data.result);
		if (data.result == HTTP_STATUS.OK) {
			console.log('republicToken','성공');
			store.commit('UserStore/currentUser', data.data);
			return true;
		} else {
			store.dispatch('UserStore/logout');
			return false;
		}
		
	} catch (err) {
		store.dispatch('UserStore/logout');
		return false;
	}
	
}

const returnData = (data: ApiResponse | null) => {
	if (!data) {
		data = {
			result : 500,
			msg : '',
			data : null
		}
	}

	return data;
}