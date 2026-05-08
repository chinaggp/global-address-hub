<template>
  <header class="sticky top-0 z-20 border-b border-brand-border/80 bg-white/95 shadow-sm backdrop-blur">
    <div class="container-page flex min-h-[72px] items-center justify-between gap-6">
      <NuxtLink to="/" class="flex items-center gap-2 text-xl font-extrabold text-brand-ink">
        <span class="flex h-7 w-7 items-center justify-center rounded-full bg-brand-blue text-sm text-white">◆</span>
        <span>Global<span class="text-brand-blue">AddressHub</span></span>
      </NuxtLink>
      <nav class="hidden items-center gap-9 text-sm font-medium text-brand-ink lg:flex">
        <NuxtLink class="text-brand-blue" to="/">Home</NuxtLink>
        <div class="group relative">
          <button class="flex items-center gap-1.5 hover:text-brand-blue" type="button">
            Countries
            <span class="text-xs">⌄</span>
          </button>
          <div
            class="invisible absolute left-0 top-full z-30 mt-3 min-w-48 rounded-lg border border-brand-border bg-white p-2 opacity-0 shadow-card transition group-hover:visible group-hover:opacity-100"
          >
            <NuxtLink
              v-for="country in countryOptions"
              :key="country.code"
              class="block rounded-md px-3 py-2 text-sm text-slate-700 hover:bg-brand-surface hover:text-brand-blue"
              :to="country.slug"
            >
              {{ country.name }}
            </NuxtLink>
          </div>
        </div>
        <span>API</span>
        <a class="hover:text-brand-blue" href="/#faq">FAQ</a>
        <span>Blog</span>
        <NuxtLink class="hover:text-brand-blue" to="/contact">About</NuxtLink>
      </nav>
      <div class="flex items-center gap-2 text-sm font-medium text-brand-ink">
        <span class="text-lg">◉</span>
        <span class="hidden sm:inline">English</span>
        <span class="text-xs">⌄</span>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { countryOptions } from '~/data/country-pages'
</script>
