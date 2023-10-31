const {defineConfig} = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8087',
                ws: true,
                changeOrigin: true,
                credentials:true,
                pathRewrite:{
                    '^/api':'/'  //替换成
                }
            },
        },
    }
})
