# Guía de Contribución - Proyecto Colaborativo (Control de Versiones)

Gracias por contribuir al proyecto. Este documento explica **cómo trabajaremos en equipo usando Git y GitHub**, manteniendo un flujo de trabajo ordenado y colaborativo.

---

## Integrantes y Roles

| Integrante | Rol principal |
|-------------|----------------|
| **Gerardo** | Líder del proyecto, creador del repositorio, configuración del repositorio y gestor de PR |
| **Josue** | Desarrollador backend  |
| **Alexander** | Encargado de resolver conflictos, documentación del proyecto y Testing |
| **Ana** | Diseñadora de la interfaz principal del sistema |
| **Jennifer** | Responsable de reportes y gestión de la base de datos |


---

## Convención de nombres de ramas

Cada integrante trabaja en su propia rama siguiendo este formato:

**Ejemplos reales del equipo:**
- `feature/gerardo-login` → (líder, backend del módulo de login)  
- `chore/josue-carrito` → (backend - mantenimiento del módulo de carrito de compras)  
- `fix/alexander-conflictos` → (gestión y resolución de conflictos en Git/GitHub)  
- `docs/ana-interfaz` → (diseño de la interfaz y documentación visual)  
- `feature/jennifer-reportes` → (reportes y base de datos)

---

## Formato de mensajes de commit

Usaremos mensajes **claros, atómicos y con prefijo semántico**:

| Prefijo | Descripción | Ejemplo |
|----------|--------------|----------|
| `feat:` | Nueva funcionalidad | `feat: agregar validación al login` |
| `fix:` | Corrección de error | `fix: corregir error en formulario de registro` |
| `docs:` | Cambios en documentación | `docs: actualizar README con nuevas capturas` |
| `chore:` | Mantenimiento o limpieza | `chore: actualizar .gitignore` |


## Procedieminto Pull Requests y revisiones (incluyendo revision cruzada)

### Flujo de trabajo colaborativo Git

```bash
# 1. Cambiar a tu rama de trabajo
git checkout <mi_rama>

# 2. Actualizar tu rama con lo último de 'main'
git pull origin <nombre_de_la_rama_mas_actualizada_del_compañero>

# 3. Realizar cambios en el proyecto (código, HTML, etc.)

# Después de trabajar, preparar los cambios:
git add .

# 4. Confirmar los cambios con un mensaje descriptivo
git commit -m "mensaje claro de lo que hiciste"

# 5. Subir tu trabajo a tu rama remota
git push origin <mi_rama>

# 6. Unir cambios a main un PR (cuando terminas una funcionalidad)

Recomendado: hacerlo con un Pull Request (PR) desde GitHub, para que los demás revisen antes de mezclar.
```

### Flujo de trabajo colaborativo GitHub

El Pull Request(PR) de cualquiera de los integrante se enviara como solicitud al creador de repositorio para poder aplicar el cambio en el main.
Una vez aprobado el PR, el integrante debe actualizar su rama local ejecutando: git pull origin main para mantener sincronizado su entorno.

## Revisión cruzada de PRs

Antes de que el líder apruebe un **Pull Request (PR)**, otro integrante del equipo debe revisarlo.

### Rotación de revisión

| Quien hace PR | Quien revisa |
|----------------|---------------|
| **Josue** | Alexander |
| **Alexander** | Ana |
| **Ana** | Jennifer |
| **Jennifer** | Josue |
| **Gerardo** | Todos pueden revisar documentación |

---

### Responsabilidades del revisor

- Verificar que el código funcione correctamente.  
- Comentar si hay errores o posibles mejoras.  
- Confirmar con **“Approve”** si todo está correcto.

---

**Nota:**  
**Gerardo** es quien realiza el **merge final al main**.

## Reglas de ramas y etiquetas

### Branch Rules

- Nadie puede hacer **push directo al main**.  
- El **main** solo se actualiza mediante **Pull Requests aprobados**.  
- Cada módulo o cambio grande debe desarrollarse en una **rama distinta**.

---

### Tag Rules

Cada versión estable se etiqueta con el siguiente formato utilizando **versionado semántico**:

| Tipo | Descripción | Ejemplo |
|------|--------------|----------|
| **v1.0.0** | Primera versión estable | `v1.0.0` |
| **v1.1.0** | Pequeñas mejoras o nuevas funciones | `v1.1.0` |
| **v2.0.0** | Cambios grandes o rediseño total | `v2.0.0` |

**Guía:**
- **major:** cambios grandes o incompatibles con versiones anteriores.  
- **minor:** nuevas funcionalidades que no rompen compatibilidad.  
- **patch:** correcciones menores o bugs.

**Solo el líder del proyecto (Gerardo)** puede crear o aprobar tags oficiales.

**Comandos para crear un tag:**

```bash
# Crear un tag con descripción
git tag -a v1.0.0 -m "Versión inicial estable"

# Subir el tag al repositorio remoto
git push origin v1.0.0
```

## Uso de Forks, upstream y flujo sincronizado

### Flujo cambiando a frok y luego el nuevo flujo de trabajo con fork (usamos upstream)

Cada integrante (menos el líder)

Entra al repo del líder en GitHub.

Clic en Fork → Create Fork (se crea su copia personal).

En la consola (sin borrar nada):
```bash
# Quitar el remoto actual
git remote remove origin

# Agregar su fork como origin
git remote add origin https://github.com/<suUsuario>/<proyecto>.git

# Agregar el repo del líder como upstream
git remote add upstream https://github.com/<usuarioLider>/<proyecto>.git

Verificar:
git remote -v

Debe mostrar:
origin   tu fork
upstream repo del líder

Subir su trabajo actual:
git push -u origin <mi_rama>
```

```bash
# 1. Cambiar a tu rama de trabajo
git checkout <mi_rama>

# 2. Traer los últimos cambios del repositorio principal (del líder)
git fetch upstream
git merge upstream/main

# 3. Realizar cambios en el proyecto (código, HTML, etc.)

# 4. Preparar los cambios
git add .

# 5. Confirmar los cambios con un mensaje claro
git commit -m "mensaje claro de lo que hiciste"

# 6. Subir tu trabajo a tu fork remoto (tu propio repositorio)
git push origin <mi_rama>
```

## Gestión de Issues, Milestones y Tablero de Proyecto

### Issues

Cada tarea, mejora o error debe registrarse como un **Issue** con:

- **Título claro y descriptivo**  
- **Asignado a un integrante**  
- **Etiqueta correspondiente** (*bug*, *enhancement*, *docs*, etc.)

---

### Milestones

Usamos **milestones** para agrupar *issues* o *pull requests* relacionados con una entrega o sprint.

**Ejemplo:**

- **Milestone 1:** Login y Registro  
- **Milestone 2:** Carrito de Compras  
- **Milestone 3:** Reportes Finales  

---

### Tablero del Proyecto (Kanban)

Organizado en columnas:

| Estado | Descripción |
|--------|--------------|
| **To Do** | Tareas pendientes |
| **In Progress** | En desarrollo |
| **Review** | Esperando revisión |
| **Done** | Completadas |

Cada integrante debe mover sus tareas según su avance.