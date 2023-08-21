import axios from "axios";
import store from '@/store/index';

const instance = axios.create({
	baseURL: "http://localhost:8080",
	withCredentials: true
});


instance.interceptors.request.use(config => {
	
	const accessToken = sessionStorage.getItem('authorization');
	console.log('request');
	config.headers['Content-Type'] = 'application/json';
	if (accessToken) config.headers['Authorization'] = `Bearer ${accessToken}`;
	console.log('asda');
    return config;
}, err => {
	console.log(err);
	return Promise.reject(err);
});

instance.interceptors.response.use(response => response, async (err) => {
	
	const { config, response: { status } } = err;
	
	if (status == 401) {
		// 401에러 && 토큰 만료인 경우 새로 받아옴
		if (err.response.data && err.response.data.msg === 'expired') {
			const res = await republicToken();
			if (res) {
				config.headers['Authorization'] = `Bearer ${sessionStorage.getItem('authorization')}`;
				return axios(config);
			} 
				
		}
	}
	
	return Promise.reject(err);
})

export const callGetApi = async(url, params) => {
	let encUrl = encodeURI(url);
	
	try {
		let result = await instance.get(encUrl + appendGetParams(params));
		return result.data;		
	} catch (err) {
		
		return err.response.data;
	}
	
	
};

export const callPostApi = async(url, params) => {
	let headers = {};
	let encUrl = encodeURI(url);
	
	const token = "";
	
	if (token != "") {
		headers['Authorization'] = 'Bearer ' + token;	
	}
	
	const _axios = axios.create({
		baseURL: "http://localhost:8080",
		withCredentials: true,
		headers
	});
	
	try {
		let result = await _axios.post(encUrl, params);
		return result.data;		
	} catch (err) {
		console.log(err.response);
		return err.response.data;
	}
	
	
};

const appendGetParams = (params) => {
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
		console.log(res.data.data);
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