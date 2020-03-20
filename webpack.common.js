const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    entry: {
        adminpanel : path.join(__dirname, 'src', 'main', 'vue', 'adminpanel.js'),
        authority : path.join(__dirname, 'src', 'main', 'vue', 'authority.js')
},
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/i,
                use: ['style-loader', 'css-loader'],
            },
        ]
    },
    plugins: [
        new VueLoaderPlugin(),
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'vue'),
            path.join(__dirname, 'node_modules'),
        ],
    }
};

