# PinterestFeed

Proyecto desarrollado en **Kotlin** con **Jetpack Compose** y **Material 3**, inspirado en el diseño visual de Pinterest.  
La aplicación muestra un feed de imágenes en cuadrícula con desplazamiento infinito, navegación entre pantallas y una vista de detalle para cada imagen.

---

## Descripción general

PinterestFeed es una aplicación que simula un muro de fotos dinámico, utilizando paginación para cargar contenido de manera progresiva.  
Incluye restauración de posición al volver al feed, un diseño adaptativo y manejo eficiente de imágenes a través de la librería **Coil**.

---

## Características principales

- Diseño tipo Pinterest con `LazyVerticalStaggeredGrid`
- Paginación automática con **Paging 3**
- Carga de imágenes optimizada con **Coil**
- Navegación entre pantallas con **Navigation Compose**
- Restauración del scroll al regresar del detalle
- Interfaz moderna basada en **Material 3**

---

## Tecnologías utilizadas

| Componente | Descripción |
|-------------|--------------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose / Material 3 |
| Navegación | Navigation Compose |
| Imágenes | Coil (con caché habilitada) |
| Arquitectura | MVVM con ViewModel y StateFlow |
| Paginación | Paging 3 |
| IDE | Android Studio Iguana / Jellyfish+ |

---

## Estructura del proyecto

<img width="607" height="706" alt="imagen_2025-10-16_233206894" src="https://github.com/user-attachments/assets/4af7dc25-a695-4fcd-94f1-c8120d69b523" />



---

## Capturas de pantalla

| Pantalla | Captura |
|-----------|----------|
| Feed principal | <img width="512" height="916" alt="feed_screen" src="https://github.com/user-attachments/assets/1d2bd115-40a0-4398-8724-075358d45796" />
hots/feed_screen.png) |
| Detalle de imagen |  <img width="365" height="797" alt="detail_screen" src="https://github.com/user-attachments/assets/b5545d5d-d2aa-4fe4-b6c0-2c8787e93b97" />
|



## Funcionamiento general

1. El `FeedViewModel` gestiona la paginación y el estado de las imágenes.  
2. `FakeApi.kt` genera imágenes aleatorias desde **picsum.photos**.  
3. El `LazyVerticalStaggeredGrid` muestra las imágenes en formato tipo muro.  
4. Al seleccionar una imagen, se navega hacia `DetailScreen.kt` mostrando el título, ID y dimensiones.  
5. Al regresar, la posición del scroll se mantiene gracias al estado guardado.

---

## Requisitos para ejecutar el proyecto

- Android Studio **Iguana** o superior  
- SDK mínimo: **24**  
- Conexión a Internet (para cargar imágenes)

---

## Ejecución

```bash
git clone https://github.com/samuelrobledo52/PinterestFeed.git

## Demostración en video

A continuación se muestra una breve demostración del funcionamiento del proyecto:

<video src="https://github.com/samuelrobledo52/PinterestFeed/assets/TU-LINK-DE-VIDEO.mp4" width="320" controls></video>




