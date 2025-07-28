# Spring Security: Deux façons de configurer l'authentification avec une page de login personnalisée

Ce document explique deux façons d'utiliser une page de connexion personnalisée avec Spring Security, avec ou sans URLs personnalisées.

---

## 1. **Login personnalisé avec les URLs PAR DÉFAUT de Spring Security**

### Configuration Java (SecurityConfig.java)

```java
.formLogin(form -> form
    .loginPage("/login") // page personnalisée
    .defaultSuccessUrl("/", true) // redirection après login
)
.logout(logout -> logout
    .logoutSuccessUrl("/") // redirection après logout
    .clearAuthentication(true)
    .invalidateHttpSession(true)
    .deleteCookies("JSESSIONID")
)
```

### Pages à créer

* `src/main/resources/templates/login.html`
* `src/main/resources/templates/index.html`

### Contrôleurs à créer

**AUCUN** contrôleur `POST` ou `GET` pour `/login` ou `/logout` n'est nécessaire. ✅

> Spring gère automatiquement la requête `POST` vers `/login` et la déconnexion via `/logout`

### ❄ Ce que Spring gère par défaut

* Si le login échoue → redirection vers `/login?error`
* Si logout réussi → redirection vers `/login?logout` (ou `logoutSuccessUrl` si redéfini)

### Pour gérer les erreurs dans `login.html` :

```html
<!-- Erreur de connexion -->
<div th:if="${param.error}" class="alert alert-danger">
    Mauvais identifiant ou mot de passe.
</div>

<!-- Succès de déconnexion -->
<div th:if="${param.logout}" class="alert alert-success">
    Vous êtes déconnecté.
</div>
```

---

## 2. **Login personnalisé avec des URLs CUSTOM (ex: `/admin/login`, `/admin/logout`)**

### Configuration Java

```java
.formLogin(form -> form
    .loginPage("/admin/login") // page personnalisée
    .loginProcessingUrl("/admin/login") // POST ici traité par Spring
    .defaultSuccessUrl("/", true)
)
.logout(logout -> logout
    .logoutUrl("/admin/logout") // URL personnalisée de déconnexion
    .logoutSuccessUrl("/admin/login?logout")
    .invalidateHttpSession(true)
    .clearAuthentication(true)
    .deleteCookies("JSESSIONID")
)
```

### Pages à créer

* `src/main/resources/templates/admin/login.html`
* `src/main/resources/templates/index.html` (ou accueil custom)
* ✉ `src/main/resources/templates/admin/login_error.html` (si tu veux une page d'erreur de login personnalisée)

### Contrôleurs à créer

```java
// GET uniquement pour afficher le formulaire de login
@GetMapping("/admin/login")
public String loginPage() {
    return "admin/login";
}
```

> Aucun `POST /admin/login` à créer → géré automatiquement par Spring ✅
> Aucun `POST /admin/logout` à créer → Spring gère aussi ça automatiquement ✅

### ❄ Comportement de Spring avec URLs custom

* Si `/admin/login` échoue : redirection automatique vers `/admin/login?error`
* Si `/admin/logout` réussi : redirection vers `/admin/login?logout`

### ➕ Gérer les messages dans `admin/login.html`

```html
<div th:if="${param.error}" class="alert alert-danger">
    Identifiants invalides !
</div>
<div th:if="${param.logout}" class="alert alert-success">
    Vous avez été déconnecté.
</div>
```

---

## Récapitulatif des différences

| Critère                  | URLs par défaut         | URLs personnalisées            |
| ------------------------ | ----------------------- | ------------------------------ |
| Page login personnalisée | `/login`                | `/admin/login`                 |
| URL de soumission login  | `/login`                | `/admin/login`                 |
| URL de déconnexion       | `/logout`               | `/admin/logout`                |
| Contrôleur GET           | `GET /login` facultatif | `GET /admin/login` obligatoire |
| Contrôleur POST          | Aucun à créer           | Aucun à créer                  |
| Messages d’erreur        | `?error`, `?logout`     | `?error`, `?logout`            |

---

On peux adapter la structure des URLs et des vues à nos besoins, tout en gardant la puissance de Spring Security qui gère les flux POST de login/logout automatiquement.
