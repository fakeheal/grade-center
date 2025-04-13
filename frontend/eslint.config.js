import js from '@eslint/js';
import plugin from 'eslint-plugin-react';

export default [
  js.configs.recommended,
  plugin.configs.flat.recommended,
  plugin.configs.flat['jsx-runtime']
]
