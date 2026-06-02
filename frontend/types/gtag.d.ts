/**
 * window.dataLayer 类型声明（GA4 gtag.js 所需）
 */
export {}

declare global {
  interface Window {
    dataLayer: unknown[]
  }
}
