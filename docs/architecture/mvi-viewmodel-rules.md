# MVI ViewModel Kuralları

Bu döküman LyraApp'teki her MVI ViewModel'inin uyması gereken yapısal kuralları tanımlar.

---

## Zorunlu Şablon

```kotlin
@HiltViewModel
class <Feature>ViewModel @Inject constructor(
    private val <feature>Repository: <Feature>Repository,
) : ViewModel() {

    private val _state = MutableStateFlow(<Feature>State())
    val state: StateFlow<<Feature>State> = _state.asStateFlow()

    private val _effect = Channel<<Feature>Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: <Feature>Intent) {
        when (intent) {
            // tüm intent'ler burada karşılanır
        }
    }

    private fun sendEffect(effect: <Feature>Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
```

---

## Kurallar

### 1. Anotasyon

`@HiltViewModel` ve `@Inject constructor` zorunludur. Manuel oluşturma yapılmaz.

### 2. State

```kotlin
private val _state = MutableStateFlow(<Feature>State())
val state: StateFlow<<Feature>State> = _state.asStateFlow()
```

- State güncellemeleri daima `_state.update { it.copy(...) }` ile yapılır.
- `_state.value = ...` doğrudan atama kullanılmaz.

### 3. Effect

```kotlin
private val _effect = Channel<<Feature>Effect>(Channel.BUFFERED)
val effect = _effect.receiveAsFlow()
```

- `Channel.BUFFERED` kullanılır; `Channel.CONFLATED` veya `SharedFlow` kullanılmaz.
- Effect gönderimi her zaman `sendEffect()` yardımcı fonksiyonu üzerinden yapılır.

### 4. Tek Giriş Noktası

```kotlin
fun onIntent(intent: <Feature>Intent)
```

- UI'dan ViewModel'e gelen tüm etkileşimler bu fonksiyon üzerinden akar.
- `onPhoneNumberChange(value)`, `onLoginClick()` gibi ayrı public fonksiyonlar oluşturulmaz.

### 5. Async İşlem Şablonu

```kotlin
private fun handle<Action>() {
    viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        repository.<operation>()
            .onSuccess { sendEffect(<Feature>Effect.NavigateTo<Hedef>) }
            .onFailure { _state.update { s -> s.copy(error = it.message) } }
        _state.update { it.copy(isLoading = false) }
    }
}
```

- Async işlem öncesi `isLoading = true`, sonrası `isLoading = false` set edilir.
- Hata durumunda `state.error` doldurulur; ayrı bir Effect gönderilmez.

### 6. Bağımlılık Enjeksiyonu

- ViewModel daima **interface** tipini alır; somut implementasyonu almaz.
- Geliştirme aşamasında Hilt, `di/<Feature>Module.kt` üzerinden Fake implementasyonu bağlar.

```kotlin
// DOGRU
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,  // interface
)

// YANLIS
class LoginViewModel @Inject constructor(
    private val authRepository: FakeAuthRepository,  // somut sınıf
)
```

---

## Route'ta Effect Tüketimi

`<Feature>Route` içinde effect, `LaunchedEffect(Unit)` bloğunda `collect` edilir:

```kotlin
LaunchedEffect(Unit) {
    viewModel.effect.collect { effect ->
        when (effect) {
            <Feature>Effect.NavigateTo<Hedef> -> { /* navController.navigate(...) */ }
        }
    }
}
```

- `receiveAsFlow()` ile alınan Channel flow'u `LaunchedEffect` dışında collect edilmez.
- Effect'ler `collectAsStateWithLifecycle` ile değil, `collect` suspend fonksiyonu ile tüketilir.
