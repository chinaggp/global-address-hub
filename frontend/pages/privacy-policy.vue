<template>
  <section class="container-page py-14">
    <div class="max-w-3xl">
      <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
        For software testing, form testing, and educational use only.
      </p>
      <h1 class="mt-4 text-4xl font-bold text-brand-ink">Privacy Policy</h1>
      <div class="mt-8 space-y-6 text-base leading-7 text-slate-600">
        <p>
          GlobalAddressHub provides generated address sample data for software testing, form testing, QA, and
          educational use only. The frontend does not ask visitors to submit personal address data.
        </p>
        <p>
          Server logs may record basic technical information such as request time, browser type, and approximate
          network information for reliability and abuse prevention.
        </p>
        <p>
          Google Analytics and Google AdSense may use Cookie technology when enabled on this site. These Cookies can
          help measure traffic, improve page quality, support advertising operations, and maintain site reliability.
          Visitors can manage Cookies through their browser settings.
        </p>
        <p>
          Contact us through the contact page for privacy questions about this tool.
        </p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
const config = useRuntimeConfig()
const siteUrl = String(config.public.siteUrl || 'https://globaladdresshub.com').replace(/\/$/, '')

useHead({
  title: 'Privacy Policy',
  meta: [
    {
      name: 'description',
      content:
        'Privacy Policy for GlobalAddressHub, an address sample generator for software testing and educational use only.'
    }
  ],
  link: [{ rel: 'canonical', href: `${siteUrl}/privacy-policy` }]
})
</script>
