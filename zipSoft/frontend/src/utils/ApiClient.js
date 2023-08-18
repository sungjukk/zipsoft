import axios from "axios";

export const callGetApi = async(url, params) => {
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
		let result = await _axios.get(encUrl + appendGetParams(params));
		return result.data;		
	} catch (err) {
		console.log(err.response);
		return err.response;
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
		console.log(result.data);
		return result.data;		
	} catch (err) {
		console.log(err.response);
		return err.response;
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