# MVIPhotoPicker
### PhotoPicker using MVI pattern with lightweight ViewModel

## *****Tech Stack***** 
| Category | Tech |
|:---|:---------------------------------------------------------------------------|
| Language | Kotlin |
| Architecture | Clean-Architecture, Multi-Module |
| Asynchronous | Coroutine, Flow |
| DI | Dagger-Hilt |
| Build | Version Catalog, Precompiled-Script-Plugin   |
| ETC | Jetpack-Compose, Coil |
</br>


## *****Basics of MVI***** 
![image](https://github.com/user-attachments/assets/02507170-0e6e-4d89-970f-a2fa033bdcd8)

MVI (Model-View-Intent) is a reactive architectural pattern inspired by Redux, focusing on managing a single immutable state.  
`Model` handles state and business logic, `View` passively renders state changes, and `Intent` represent user actions that trigger updates.  
This pattern ensures predictable state management, promotes immutability, and offers clear separation of concerns for easier testing and maintenance.  

</br>

## *****MVI with lightweight ViewModel***** 
![image](https://github.com/user-attachments/assets/e6c5f497-7489-4b55-a041-fdc370992509)

In the previous implementation, the ViewModel handled both business logic and state management directly, leading to bloated code. Additionally, since state could be modified in multiple functions, unintended state changes often occurred.

To solve these issues, it was necessary to refactor the ViewModel by clearly separating responsibilities and ensuring that state changes only occur in a single function.

To achieve this, the responsibility for state changes was delegated to a separate class called `Reducer`. The `Reducer` takes the current state and a `Mutation` (an intended state change) to generate a new state, 
ensuring that state modifications only happen in a defined location.

The business logic responsibility was moved to the **Action Processor**. Instead of the ViewModel directly handling business logic, the Action Processor now processes actions and emits a flow of Mutations and Events. 
(Mutations are emitted when state changes are needed, while Events are emitted when UIEvent are required.)

There might be concerns that Action Processors could become bloated, but by separating Action Processors based on business logic concerns, 
we can keep each class manageable. For example, business logic related to client interactions (e.g., clicks, swipes) can be handled by a `UserActionProcessor`,
while basic UI-related tasks (e.g., initial API calls) can be handled by a `DefaultActionProcessor`.

This restructuring allows the ViewModel to become lightweight, with its responsibilities limited to invoking Action Processors and handling Mutations via the Reducer. As a result, 
responsibilities are clearly separated, state management is more predictable, and maintainability, code readability, and scalability are significantly improved.

</br>

## *****Module Dependency Graph***** 
</br>

![image](https://github.com/user-attachments/assets/98edca13-cc42-4356-950c-5645e4192a85)

</br>

## *****Package Structure***** 
```
├── app
├── buildSrc
│   ├── convention
│   └── gradle.configure
├── core
│   ├── data
│   │   ├── album
│   │   │   ├── external
│   │   │   │   ├── model
│   │   │   │   └── repository
│   │   │   └── internal
│   │   │       ├── di
│   │   │       └── repository
│   │   └── sticker
│   │       ├── external
│   │       │   ├── model
│   │       │   └── repository
│   │       └── internal
│   │           ├── di
│   │           └── repository
│   ├── design-system
│   │       ├── component
│   │       └── theme
│   ├── external-data
│   │       ├── album
│   │       └── internal
│   ├── permission
│   └── util
└── feature
    ├── album-list
    ├── base
    ├── main
    ├── photo-detail
    └── photo-list
```
</br>


## *****Reference***** 
https://proandroiddev.com/lighten-mvi-architecture-delegate-responsibilities-to-new-components-7ea27ea54021
