<template>
  <footer class="bg-slate-950 text-slate-300">
    <div class="container-page grid gap-10 py-8 md:grid-cols-4">
      <div>
        <p class="flex items-center gap-2 text-base font-bold text-white">
          <span class="text-brand-blue">◆</span>
          Global<span class="-ml-2 text-brand-blue">AddressHub</span>
        </p>
        <p class="mt-4 text-sm leading-6 text-slate-300">
          Generate random addresses worldwide. Free, fast and easy to use.
        </p>
        <div class="mt-4 flex gap-4 text-sm">
          <span class="footer-social">t</span>
          <span class="footer-social">f</span>
          <span class="footer-social">g</span>
          <span class="footer-social">@</span>
        </div>
      </div>
      <div>
        <h2 class="text-sm font-semibold text-white">Popular Countries</h2>
        <ul class="mt-4 space-y-2 text-sm">
          <li v-for="country in countryOptions.slice(0, 5)" :key="country.code">
            <NuxtLink class="hover:text-white" :to="country.slug">{{ country.name }}</NuxtLink>
          </li>
        </ul>
      </div>
      <div>
        <h2 class="text-sm font-semibold text-white">Resources</h2>
        <ul class="mt-4 space-y-2 text-sm">
          <li><span>API (Coming Soon)</span></li>
          <li><span>Blog</span></li>
          <li><a class="hover:text-white" href="/#faq">FAQ</a></li>
          <li><NuxtLink class="hover:text-white" to="/contact">About Us</NuxtLink></li>
          <li><NuxtLink class="hover:text-white" to="/contact">Contact</NuxtLink></li>
        </ul>
      </div>
      <div>
        <h2 class="text-sm font-semibold text-white">Legal</h2>
        <ul class="mt-4 space-y-2 text-sm">
          <li><NuxtLink class="hover:text-white" to="/privacy-policy">Privacy Policy</NuxtLink></li>
          <li><NuxtLink class="hover:text-white" to="/terms-of-use">Terms of Use</NuxtLink></li>
          <li><NuxtLink class="hover:text-white" to="/disclaimer">Disclaimer</NuxtLink></li>
        </ul>
      </div>
    </div>
    <p class="pb-3 text-center text-sm text-slate-300">© 2024 GlobalAddressHub.com. All rights reserved.</p>
  </footer>
</template>

<script setup lang="ts">
import { countryOptions } from '~/data/country-pages'
</script>
