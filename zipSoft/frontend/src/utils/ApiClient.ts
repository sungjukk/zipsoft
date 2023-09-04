import axios, {AxiosRequestConfig, AxiosProgressEvent, ResponseType} from "axios";
import store from '@/store/index';
import { globals } from "@/main";

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
	console.log(config.headers);
	//config.headers['Content-Type'] = 'application/json';
	if (accessToken) config.headers['Authorization'] = `Bearer ${accessToken}`;
    return config;
}, err => {
	return Promise.reject(err);
});

instance.interceptors.response.use(response => response, async (err) => {
	
	const { config, response: { status } } = err;
	
	if (status == 401) {
		// 401에러 && 토큰 만료인 경우 새로 받아옴
		console.log('401');
		if (err.response.data && err.response.data.msg === 'expired') {
			const res = await republicToken();
			if (res) {
				config.headers['Authorization'] = `Bearer ${sessionStorage.getItem('authorization')}`;
				return axios(config);
			} else {
				await store.dispatch('UserStore/logout');
			} 
				
		}
	} 

	return Promise.reject(err);
	
})

const sleep = async (ms: number) => {
	const wakeUpTime = Date.now() + ms;
	while (Date.now() < wakeUpTime) {}
}

export const callGetApi = async(url : string, params? : object) => {
	const encUrl = encodeURI(url);
	
	try {
		const result = await instance.get(encUrl + appendGetParams(params));
		return result.data;		
	} catch (err) {
		if (axios.isAxiosError(err)) {
			// axios에서 발생한 error
			return err.response?.data;
		}
	}
	
	
};

export const callPostApi = async(url:string, params?:object) => {
	const encUrl = encodeURI(url);
	try {
		const result = await instance.post(encUrl, params);
		return result.data;		
	} catch (err) {
		if (axios.isAxiosError(err)) {
			return err.response?.data;			
		}
	}
	
	
};

export const callFileApi = async(url:string, params?:object) => {
	const headers = {
		"Content-Type": "multipart/form-data"
	};
	try {
		const result = await instance.post(url, params, {headers});
		return result.data;		
	} catch (err) {
		if (axios.isAxiosError(err)) {
			return err.response?.data;			
		}
	}
};

export const callFileDownApi = async(url:string, params?:object) => {
	const config = {
		onDownloadProgress: (event : any) => {
			const per = Math.round((event.loaded * 100) / event.total);
        	console.log(`${event.loaded} / ${event.total} ${per}`);
			console.log(event);
		},
		responseType : 'blob' as ResponseType
	}

	try {
		const result = await instance.get(url, config);
		let fileName = result.headers['content-disposition'].split('filename=')[1].replaceAll(/"/gi,'');

		const fileUrl = window.URL.createObjectURL(new Blob([result.data], {
			type: result.headers['content-type'],
		  }));
		const link = document.createElement('a');
		link.href = fileUrl;
		link.download = decodeURI(fileName);
		document.body.appendChild(link);
		link.click();

	} catch (err) {
		if (axios.isAxiosError(err)) {	
			
		} 

		globals.$alert('다운로드에 실패하였습니다');
		return false;
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
		const res = await instance.get('/auth/republishToken');
		if (res.data.result == 200) {
			store.commit('UserStore/currentUser', res.data.data);
			return true;
		} else {
			return false;
		}
		
	} catch (err) {
		return false;
	}
	
	
}