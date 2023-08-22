const { CleanWebpackPlugin } = require("clean-webpack-plugin");
const {merge} = require('webpack-merge');



const buildConfig = process.env.NODE_ENV === 'production'  ? require('./prod.config') :
				    process.env.NODE_ENV === 'development' ? require('./dev.config')  :
											                 require('./local.config');

module.exports = merge(buildConfig, {
  transpileDependencies: true,
  configureWebpack: {
	output: {
	  filename: 'js/[name].[hash].js',
      chunkFilename: 'js/[name].[hash].js' 
	},
	plugins: [new CleanWebpackPlugin()]  
  },
  devServer: {
        // 사용자 정의 환경 변수에서 VUE_APP_PORT가 있으면 사용하고
        // 없으면 3000 포트로 개발서버를 실행합니다.
        port: process.env.VUE_APP_PORT || 3000,
        hot : true,	//리로드 설정
    }
});
