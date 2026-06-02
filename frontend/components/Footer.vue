<template>
  <footer class="bg-slate-950 text-slate-300">
    <div class="container-page grid gap-10 py-8 md:grid-cols-4">
      <div>
        <p class="flex items-center gap-2 text-base font-bold text-white">
          <span class="text-brand-blue">G</span>
          Global<span class="-ml-2 text-brand-blue">AddressHub</span>
        </p>
        <p class="mt-4 text-sm leading-6 text-slate-300">
          {{ $t('footer.description') }}
        </p>
        <div class="mt-4 flex gap-4 text-sm">
          <span class="footer-social">t</span>
          <span class="footer-social">f</span>
          <span class="footer-social">g</span>
          <span class="footer-social">@</span>
        </div>
      </div>
      <div>
        <h2 class="text-sm font-semibold text-white">{{ $t('footer.popular_countries') }}</h2>
        <ul class="mt-4 space-y-2 text-sm">
          <li v-for="country in countryOptions.slice(0, 5)" :key="country.code">
            <NuxtLink class="hover:text-white" :to="localePath(country.slug)">{{ country.name }}</NuxtLink>
          </li>
        </ul>
      </div>
      <div>
        <h2 class="text-sm font-semibold text-white">{{ $t('footer.resources') }}</h2>
        <ul class="mt-4 space-y-2 text-sm">
          <li><span class="text-slate-500 cursor-not-allowed select-none">{{ $t('footer.api_coming_soon') }}</span></li>
          <li><NuxtLink class="hover:text-white transition-colors duration-200" :to="localePath('/blog')">{{ $t('nav.blog') }}</NuxtLink></li>
          <li><NuxtLink class="hover:text-white transition-colors duration-200" :to="`${localePath('/')}#faq`">{{ $t('nav.faq') }}</NuxtLink></li>
          <li><NuxtLink class="hover:text-white transition-colors duration-200" :to="localePath('/contact')">{{ $t('footer.about_us') }}</NuxtLink></li>
          <li><NuxtLink class="hover:text-white transition-colors duration-200" :to="localePath('/contact')">{{ $t('footer.contact') }}</NuxtLink></li>
        </ul>
      </div>
      <div>
        <h2 class="text-sm font-semibold text-white">{{ $t('footer.legal') }}</h2>
        <ul class="mt-4 space-y-2 text-sm">
          <li><NuxtLink class="hover:text-white" :to="localePath('/privacy-policy')">{{ $t('legal.privacy.title') }}</NuxtLink></li>
          <li><NuxtLink class="hover:text-white" :to="localePath('/terms-of-use')">{{ $t('legal.terms.title') }}</NuxtLink></li>
          <li><NuxtLink class="hover:text-white" :to="localePath('/disclaimer')">{{ $t('legal.disclaimer.title') }}</NuxtLink></li>
        </ul>
      </div>
    </div>
    <p class="pb-3 text-center text-sm text-slate-300">{{ $t('footer.copyright') }}</p>
  </footer>
</template>

<script setup lang="ts">
const localePath = useLocalePath()
const { localizedCountryOptions } = useCountryContent()

const countryOptions = localizedCountryOptions
</script>
