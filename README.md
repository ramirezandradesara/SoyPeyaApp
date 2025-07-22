# 🛵 SoyPeyaApp - E-Commerce MVP


## 🚀 Overview
SoyPeya is an MVP of an e-commerce application designed to provide essential shopping functionalities. 
It features a product catalog where users can browse available items, a detailed product view to display individual product information, a shopping cart to manage selected products, and a checkout process to complete purchases. 
This foundational version focuses on delivering a smooth and intuitive user experience through modern Android development practices.

---

## 🛠 Tech Stack

### Main Language
- **Kotlin**: A modern, safe, and official language for Android development that enables clean and concise code.

#### Versions
- **Minimum SDK**: 24, to support a broad range of current devices while enabling modern features.
- **Java Version**: 11, chosen for its stability and Android ecosystem support.

### Architecture
- **MVVM (Model-View-ViewModel)**: Promotes separation of concerns, enhances maintainability, and improves UI lifecycle and state management.

### Key Frameworks and Libraries

- **Hilt**: Simplifies dependency injection, reduces boilerplate, and improves modularity.
- **Retrofit + Gson**: Facilitate efficient HTTP communication and JSON serialization/deserialization.
- **Room**: Provides an abstraction layer over SQLite, enabling easy local database handling with coroutine support.
- **Navigation Component**: Manages screen navigation declaratively with safe args and back stack support.
- **Jetpack Compose**: A modern toolkit for building declarative and reactive UI in Android.
- **Material Design**: Ensures visual consistency and a user experience aligned with Google's guidelines.
- **RecyclerView**: Efficiently handles large or dynamic lists with flexibility.
- **Glide**: Efficiently loads and caches images to optimize visual performance.

---

## 🏗 Project Structure

```
com.soypeya/
├── app/                  
├── core/                 
│   ├── approutes/          
│   ├── constants/
│   └── session/          
│
├── data/
│   ├── di/ 
│   ├── local/   
│   │   ├── dao/
│   │   └── datasource/
│   ├── mappers/   
│   ├── remote/
│   │   ├── api/ 
│   │   ├── datasource/
│   │   └── dto/
│   ├── repository/    
│   └── workers/                                
│
├── feature/          
│   ├── cart/
│   ├── login/  
│   ├── orders/              
│   ├── products/         
│   ├── profile/  
│   ├── register/
│   └── navigation/              
│
└── library/                 
    ├── ui/            
    └── utils/      
           
```