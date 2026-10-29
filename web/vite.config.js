import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    // In dev, forward API calls to Spring Boot so the app can use relative URLs (no CORS needed).
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
  build: {
    outDir: 'build',
  },
  test: {
    environment: 'jsdom',
    setupFiles: './src/setupTests.js',
  },
});
