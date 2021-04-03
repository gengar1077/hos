const path = require('path');

module.exports = {
  // reactScriptsVersion: 'react-scripts' /* (default value) */,
  // style: {
  //   modules: {
  //     localIdentName: '',
  //   },
  //   css: {
  //     loaderOptions: {
  //       /* Any css-loader configuration options: https://github.com/webpack-contrib/css-loader. */
  //     },
  //     loaderOptions: (cssLoaderOptions, { env, paths }) => {
  //       return cssLoaderOptions;
  //     },
  //   },
  //   sass: {
  //     loaderOptions: {
  //       /* Any sass-loader configuration options: https://github.com/webpack-contrib/sass-loader. */
  //     },
  //     loaderOptions: (sassLoaderOptions, { env, paths }) => {
  //       return sassLoaderOptions;
  //     },
  //   },
  //   postcss: {
  //     mode: 'extends' /* (default value) */ || 'file',
  //     plugins: [],
  //     env: {
  //       autoprefixer: {
  //         /* Any autoprefixer options: https://github.com/postcss/autoprefixer#options */
  //       },
  //       stage: 3 /* Any valid stages: https://cssdb.org/#staging-process. */,
  //       features: {
  //         /* Any CSS features: https://preset-env.cssdb.org/features. */
  //       },
  //     },
  //     loaderOptions: {
  //       /* Any postcss-loader configuration options: https://github.com/postcss/postcss-loader. */
  //     },
  //     loaderOptions: (postcssLoaderOptions, { env, paths }) => {
  //       return postcssLoaderOptions;
  //     },
  //   },
  // },
  // eslint: {
  //   enable: true /* (default value) */,
  //   mode: 'extends' /* (default value) */ || 'file',
  //   configure: {
  //     /* Any eslint configuration options: https://eslint.org/docs/user-guide/configuring */
  //   },
  //   configure: (eslintConfig, { env, paths }) => {
  //     return eslintConfig;
  //   },
  //   pluginOptions: {
  //     /* Any eslint plugin configuration options: https://github.com/webpack-contrib/eslint-webpack-plugin#options. */
  //   },
  //   pluginOptions: (eslintOptions, { env, paths }) => {
  //     return eslintOptions;
  //   },
  // },
  // babel: {
  //   presets: [],
  //   plugins: [],
  //   loaderOptions: {
  //     /* Any babel-loader configuration options: https://github.com/babel/babel-loader. */
  //   },
  //   loaderOptions: (babelLoaderOptions, { env, paths }) => {
  //     return babelLoaderOptions;
  //   },
  // },
  // typescript: {
  //   enableTypeChecking: true /* (default value)  */,
  // },
  webpack: {
    // alias 修改后记得要改 tsconfig，不过 cra 后面会修改。
    // 借助 tsconfig 的 extends 字段手动配置个相关的文件
    // https://github.com/risenforces/craco-alias/issues/5
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
    // plugins: {
    //   add: [] /* An array of plugins */,
    //   remove: [] /* An array of plugin constructor's names (i.e. "StyleLintPlugin", "ESLintWebpackPlugin" ) */,
    // },
    // configure: {
    //   /* Any webpack configuration options: https://webpack.js.org/configuration */
    // },
    // configure: (webpackConfig, { env, paths }) => {
    //   return webpackConfig;
    // },
  },
  // jest: {
  //   babel: {
  //     addPresets: true /* (default value) */,
  //     addPlugins: true /* (default value) */,
  //   },
  //   configure: {
  //     /* Any Jest configuration options: https://jestjs.io/docs/en/configuration. */
  //   },
  //   configure: (jestConfig, { env, paths, resolve, rootDir }) => {
  //     return jestConfig;
  //   },
  // },
  // devServer: {
  //   /* Any devServer configuration options: https://webpack.js.org/configuration/dev-server/#devserver. */
  // },
  // devServer: (devServerConfig, { env, paths, proxy, allowedHost }) => {
  //   return devServerConfig;
  // },
  // plugins: [
  //   {
  //     plugin: {
  //       overrideCracoConfig: ({
  //         cracoConfig,
  //         pluginOptions,
  //         context: { env, paths },
  //       }) => {
  //         return cracoConfig;
  //       },
  //       overrideWebpackConfig: ({
  //         webpackConfig,
  //         cracoConfig,
  //         pluginOptions,
  //         context: { env, paths },
  //       }) => {
  //         return webpackConfig;
  //       },
  //       overrideDevServerConfig: ({
  //         devServerConfig,
  //         cracoConfig,
  //         pluginOptions,
  //         context: { env, paths, proxy, allowedHost },
  //       }) => {
  //         return devServerConfig;
  //       },
  //       overrideJestConfig: ({
  //         jestConfig,
  //         cracoConfig,
  //         pluginOptions,
  //         context: { env, paths, resolve, rootDir },
  //       }) => {
  //         return jestConfig;
  //       },
  //     },
  //     options: {},
  //   },
  // ],
};
