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



