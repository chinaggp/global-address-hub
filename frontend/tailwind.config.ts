import type { Config } from 'tailwindcss'

export default <Partial<Config>>{
  content: [
    './app.vue',
    './components/**/*.{vue,js,ts}',
    './composables/**/*.{js,ts}',
    './layouts/**/*.vue',
    './pages/**/*.vue'
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          blue: '#2563EB',
          green: '#10B981',
          surface: '#F8FAFC',
          ink: '#0F172A',
          border: '#E2E8F0'
        }
      },
      boxShadow: {
        card: '0 18px 45px rgba(15, 23, 42, 0.08)'
      }
    }
  }
}
