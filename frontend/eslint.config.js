import js from '@eslint/js';
import plugin from 'eslint-plugin-react';

export default [
  js.configs.recommended,
  plugin.configs.flat.recommended,
  plugin.configs.flat['jsx-runtime'],
  {
    rules: {
      'react/prop-types': 0 // React 19 removed prop-types
    }
  }
]
