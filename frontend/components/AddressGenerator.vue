<template>
  <section class="card p-7">
    <form class="space-y-6" @submit.prevent="generate">
      <CountrySelector v-model="selectedCountry" :countries="countries" :label="$t('generator.select_country')" />
      <RegionSelector
        v-model="selectedRegion"
        :regions="regions"
        :loading="regionsLoading"
        :label="$t('generator.select_region', { region: resolvedRegionLabel })"
      />
      <button class="btn-primary h-12 w-full gap-2 text-base" type="submit" :disabled="loading">
        <span class="text-lg">↻</span>
        {{ loading ? $t('generator.btn_generating') : $t('generator.btn_generate') }}
      </button>
    </form>

    <div class="mt-6 flex items-start gap-3 text-sm leading-6 text-slate-600">
      <span class="mt-0.5 text-brand-blue">◆</span>
      <p>{{ $t('generator.disclaimer') }}</p>
    </div>

    <p v-if="error" class="mt-4 rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">
      {{ error }}
    </p>
  </section>
</template>

<script setup lang="ts">
import type { AddressResult, CountryOption, RegionOption } from '~/types/address'

const props = withDefaults(
  defineProps<{
    defaultCountry?: string
    regionLabel?: string
  }>(),
  {
    defaultCountry: 'US',
    regionLabel: 'Region'
  }
)

const emit = defineEmits<{
  generated: [address: AddressResult]
}>()

const { locale, t } = useI18n()
const { getCountries, getRegions, getRandomAddress } = useAddressApi()
const { localizedCountryOptions } = useCountryContent()

const selectedCountry = ref(props.defaultCountry)
const selectedRegion = ref('')
const countries = ref<CountryOption[]>(localizedCountryOptions.value)
const regions = ref<RegionOption[]>([])
const loading = ref(false)
const regionsLoading = ref(false)
const error = ref('')
const resolvedRegionLabel = computed(() => (props.regionLabel === 'Region' ? t('generator.region') : props.regionLabel))

async function loadCountries() {
  try {
    const remoteCountries = await getCountries()
    countries.value = remoteCountries.length > 0 ? remoteCountries : localizedCountryOptions.value
  } catch {
    countries.value = localizedCountryOptions.value
  }
}

async function loadRegions() {
  selectedRegion.value = ''
  regions.value = []
  regionsLoading.value = true
  try {
    regions.value = await getRegions(selectedCountry.value)
  } catch {
    regions.value = []
  } finally {
    regionsLoading.value = false
  }
}

async function generate() {
  error.value = ''
  loading.value = true
  try {
    const address = await getRandomAddress(selectedCountry.value, selectedRegion.value)
    emit('generated', address)
  } catch (requestError) {
    error.value = (requestError as Error).message
  } finally {
    loading.value = false
  }
}

defineExpose({
  generate
})

watch(selectedCountry, loadRegions)
watch(locale, async () => {
  await loadCountries()
  await loadRegions()
})

onMounted(async () => {
  await loadCountries()
  await loadRegions()
  await generate()
})
</script>
