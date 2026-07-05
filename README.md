# 📚 LitQuest API - Catálogo de Libros

Aplicación de consola para buscar y gestionar libros clásicos usando la API de Gutendex.

## 🚀 Inicio Rápido

### 1. Requisitos
- Java 17+
- PostgreSQL
- Maven

### 2. Configurar Base de Datos

Crea la base de datos:
```sql
CREATE DATABASE litquest_db;
```

### 3. Configurar Conexión

Crea un archivo `.env` en la raíz del proyecto (usa `.env.example` como base):
```
DB_HOST=localhost:5432
DB_NAME=litquest_db
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

### 4. Ejecutar
```bash
mvn spring-boot:run
```

## 📖 Cómo Usar

### Menú Principal
```
1- Buscar libro por titulo
2- Listar libros registrados
3- Listar autores registrados
4- Listar autores vivos en un determinado año
5- Listar libros por idioma
6- Salir
```

### Ejemplos

**Buscar un libro:**
```
> 1
> Pride and Prejudice
```

**Ver autores vivos en 1800:**
```
> 4
> 1800
```

**Filtrar por idioma:**
```
> 5
> en    (inglés)
```

## 📚 Libros para Probar

| Título | Autor | Año | Idioma |
|--------|-------|-----|--------|
| Pride and Prejudice | Jane Austen | 1813 | en |
| Don Quijote | Cervantes | 1605 | es |
| Frankenstein | Mary Shelley | 1818 | en |
| Hamlet | Shakespeare | 1603 | en |

## 🔧 Idiomas Disponibles
- `es` - Español
- `en` - Inglés
- `fr` - Francés
- `pt` - Portugués

## ⚙️ Tecnologías
- Java 17
- Spring Boot 3.5.10
- PostgreSQL
- JPA/Hibernate

## 📝 Notas
- Solo busca libros de dominio público
- La información viene de [Gutendex API](https://gutendex.com/)
- Los libros se guardan automáticamente al buscarlos

## 🐛 Problemas Comunes

**No conecta a la base de datos:**
- Verifica que PostgreSQL esté corriendo
- Revisa usuario y contraseña en `.env`

**No encuentra un libro:**
- Solo hay libros clásicos (dominio público)
- Intenta con títulos en inglés

---

Ariel Plaza - Desarrollador BackEnd
