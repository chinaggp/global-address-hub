const siteUrl = process.env.NUXT_PUBLIC_SITE_URL || 'https://globaladdresshub.com'

const routes = [
  '/',
  '/us-address-generator',
  '/japan-address-generator',
  '/uk-address-generator',
  '/canada-address-generator',
  '/australia-address-generator',
  '/turkey-address-generator',
  '/nigeria-address-generator',
  '/privacy-policy',
  '/terms-of-use',
  '/disclaimer',
  '/contact'
]

export default defineNuxtConfig({
  compatibilityDate: '2025-01-01',
  devtools: { enabled: true },
  modules: ['@nuxtjs/tailwindcss', '@nuxtjs/sitemap', '@nuxtjs/i18n'],
  i18n: {
    locales: [
      { code: 'en', iso: 'en-US', file: 'en.json', name: 'English' },
      { code: 'zh', iso: 'zh-CN', file: 'zh.json', name: '简体中文' }
    ],
    defaultLocale: 'en',
    langDir: 'locales',
    strategy: 'prefix_except_default',
    detectBrowserLanguage: {
      useCookie: true,
      cookieKey: 'i18n_redirected',
      redirectOn: 'root'
    }
  },
  site: {
    url: siteUrl,
    name: 'GlobalAddressHub'
  },
  css: ['~/assets/css/main.css'],
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.NUXT_PUBLIC_API_BASE_URL || 'http://localhost:8080',
      siteUrl,
      googleAnalyticsId: process.env.NUXT_PUBLIC_GOOGLE_ANALYTICS_ID || ''
    }
  },
  app: {
    head: {
      htmlAttrs: { lang: 'en' },
      titleTemplate: '%s | GlobalAddressHub',
      meta: [
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
        {
          name: 'description',
          content:
            'Generate address sample data for software testing, form testing, QA, and educational use only.'
        },
        { property: 'og:site_name', content: 'GlobalAddressHub' },
        { property: 'og:type', content: 'website' },
        { property: 'og:url', content: siteUrl },
        {
          property: 'og:description',
          content:
            'GlobalAddressHub provides address sample data for software testing, form testing, QA, and educational use only.'
        }
      ],
      link: [{ rel: 'canonical', href: siteUrl }]
    }
  },
  sitemap: {
    urls: routes
  },
  tailwindcss: {
    cssPath: '~/assets/css/main.css',
    configPath: 'tailwind.config.ts'
  },
  typescript: {
    strict: true,
    typeCheck: true
  }
})
