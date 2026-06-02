<template>
  <header class="sticky top-0 z-20 border-b border-brand-border/80 bg-white/95 shadow-sm backdrop-blur">
    <div class="container-page flex min-h-[72px] items-center justify-between gap-6">
      <NuxtLink :to="localePath('/')" class="flex items-center gap-2 text-xl font-extrabold text-brand-ink">
        <img class="h-8 w-8 rounded-full" src="/logo.png" alt="GlobalAddressHub logo" />
        <span>Global<span class="text-brand-blue">AddressHub</span></span>
      </NuxtLink>
      <nav class="hidden items-center gap-9 text-sm font-medium text-brand-ink lg:flex">
        <NuxtLink class="text-brand-blue" :to="localePath('/')">{{ $t('nav.home') }}</NuxtLink>
        <div class="group relative">
          <button class="flex items-center gap-1.5 hover:text-brand-blue" type="button">
            {{ $t('nav.countries') }}
            <span class="text-xs">v</span>
          </button>
          <div
            class="invisible absolute left-0 top-full z-30 pt-3 opacity-0 transition-all group-hover:visible group-hover:opacity-100"
          >
            <div class="min-w-48 rounded-lg border border-brand-border bg-white p-2 shadow-card">
              <NuxtLink
                v-for="country in countryOptions"
                :key="country.code"
                class="block rounded-md px-3 py-2 text-sm text-slate-700 hover:bg-brand-surface hover:text-brand-blue"
                :to="localePath(country.slug)"
              >
                {{ country.name }}
              </NuxtLink>
            </div>
          </div>
        </div>
        <!-- <div class="relative group">
          <span class="text-slate-400 cursor-not-allowed flex items-center gap-1 select-none">
            {{ $t('nav.api') }}
            <span class="rounded bg-slate-100 px-1 py-0.5 text-[10px] text-slate-500 font-bold uppercase tracking-wider scale-90">Soon</span>
          </span>
        </div> -->
        <NuxtLink class="hover:text-brand-blue transition-colors duration-200" :to="`${localePath('/')}#faq`">{{ $t('nav.faq') }}</NuxtLink>
        <NuxtLink class="hover:text-brand-blue transition-colors duration-200" :to="localePath('/blog')">{{ $t('nav.blog') }}</NuxtLink>
        <NuxtLink class="hover:text-brand-blue transition-colors duration-200" :to="localePath('/contact')">{{ $t('nav.about') }}</NuxtLink>
      </nav>
      <div class="group relative flex cursor-pointer items-center gap-2 text-sm font-medium text-brand-ink">
        <span class="text-lg">◎</span>
        <span class="hidden sm:inline">{{ currentLocaleName }}</span>
        <span class="text-xs">v</span>
        <div
          class="invisible absolute right-0 top-full z-30 pt-3 opacity-0 transition-all group-hover:visible group-hover:opacity-100"
        >
          <div class="min-w-32 rounded-lg border border-brand-border bg-white p-2 shadow-card">
            <button
              v-for="localeItem in availableLocales"
              :key="localeItem.code"
              class="block w-full rounded-md px-3 py-2 text-left text-sm text-slate-700 hover:bg-brand-surface hover:text-brand-blue"
              @click="switchLocale(localeItem.code)"
            >
              {{ localeItem.name }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
const { locale, locales, setLocale } = useI18n()
const localePath = useLocalePath()
const { localizedCountryOptions } = useCountryContent()

const countryOptions = localizedCountryOptions
const availableLocales = computed(() => locales.value as Array<{ code: string; name: string }>)
const currentLocaleName = computed(() => {
  const current = availableLocales.value.find((item) => item.code === locale.value)
  return current ? current.name : 'English'
})

function switchLocale(code: string) {
  setLocale(code as 'en' | 'zh')
}
</script>
