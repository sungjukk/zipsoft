import axios from "axios";
import store from '@/store/index';

export const callGetApi = async(url, params) => {
	let headers = {};
	let encUrl = encodeURI(url);
	
	const token = sessionStorage.getItem('authorization');
	
	if (token != "") {
		headers['Authorization'] = token;	
	}
	
	const _axios = axios.create({
		baseURL: "http://localhost:8080",
		withCredentials: true,
		headers
	});
	
	try {
		let result = await _axios.get(encUrl + appendGetParams(params));
		return result.data;		
	} catch (err) {
		
		if (err.response.status == 401) {
			
			// 401에러 && 토큰 만료인 경우 새로 받아옴
			if (err.response.data && err.response.data.msg === 'expired') {
				const res = await republicToken();
				console.log(res);
				if (res) {
					callGetApi(url, params);
				} else {
					store.dispatch('UserStore/logout');
				}
			}
			
			
		}
		
		
		
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
	let headers = {
		Authorization : sessionStorage.getItem('authorization')
	};
	
	const _axios = axios.create({
		baseURL: "http://localhost:8080",
		withCredentials: true,
		headers
	});
	
	try {
		const res = await _axios.get('/auth/republishToken');
		console.log(res);
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