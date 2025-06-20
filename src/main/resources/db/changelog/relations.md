Absolutely! Here's a complete, concise **reference notes sheet** for all JPA relationship mappings: `@OneToOne`, `@OneToMany`, `@ManyToOne`, and `@ManyToMany`, including all your questions, doubts, and best practices.

---

# 🔗 JPA Relationship Mapping Notes

---

## ✅ 1. @OneToOne

### 🔹 Meaning:

> One entity is related to exactly one of another entity.

### 🔹 Typical Use Case:

* User ↔ UserProfile
* User will store fk for UserProfile as profile_id
* `@JoinColumn` on `base Entity` with name as name of column which will store fk of `referance Entity`
* `mappedBy` on `referance Entity` with value as name of field (variable with referance Entity type) in `base Entity` of fk

### 🔹 Standard Setup:

#### 🔸 Owning Side (holds FK):

```java
@Entity
class User {

    @OneToOne
    @JoinColumn(name = "profile_id")
    private UserProfile profile;
}  
```

#### 🔸 Inverse Side:

```java
@Entity
class UserProfile{

    @IgnoreJson
    @OneToOne(mappedBy = "profile")
    private User user;
}
```

* not adding anything on referance side will make unidirectinal setup and User can access profile but UserProfile cant access user

```java
@Entity
class UserProfile{

}
```

* `@JoinColumn` → on owning side
* `mappedBy = "fieldName"` → on inverse side
* `fieldName` is the Java field where `@OneToOne` is defined

### 🔹 Notes:

* Only one side can own the relationship.
* FK is in **owning side's table**
* Prefer `FetchType.EAGER` on owning side only to avoid infinite loops.
* Use `@IgnoreJson` on referance Entity field to avoid infinite loop
---

## ✅ 2. @ManyToOne + @OneToMany (Bidirectional)

### 🔹 Meaning:

> Many entities relate to one parent. One parent has many children.

### 🔹 Use Case:

* Post ↔ User (Many posts by one user)
* `nothing` on `Child Entity` or `base Entity` 
* `@OneToMany` and `@JoinColumn` on `Parent Entity` or `referance Entity`
* User can access posts but Posts can't access User 

### 🔹 Unidirectional Setup:

#### 🔸 Owning Side (many side, has FK):

```java
@Entity
class Post{

}
```

#### 🔸 Inverse Side (one side):

```java
@Entity
class User{

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Post> posts;
}
```

* `@JoinColumn` is only on owning side.
* FK column (`user_id`) is stored in `Post` table.


### 🔹 Use Case:

* Post ↔ User (Many posts by one user)
* `@ManyToOne` and `@JoinColumn` on `Child Entity` or `base Entity` 
* `@OneToMany` on `Parent Entity` or `referance Entity`


### 🔹 Setup:

#### 🔸 Owning Side (many side, has FK):

```java
@Entity
class Post{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

#### 🔸 Inverse Side (one side):

```java
@Entity
class User{

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Post> posts;
}
```


### 🔹 Notes:

* You **must** define the FK in Liquibase (if using it):

```xml
<column name="user_id" type="BIGINT"/>
```

* `mappedBy = "user"` → `user` is the field in `Post`

---

## ✅ 3. @OneToMany (Unidirectional with JoinColumn)

If you don’t want back-reference in child:

```java
@OneToMany(cascade = ALL)
@JoinColumn(name = "user_id")
private List<Hobby> hobbies;
```

* JPA will create FK in child (`hobby.user_id`)
* But you **must use `@JoinColumn`**, otherwise it creates a join table

---

## ✅ 4. @ManyToMany

### 🔹 Meaning:

> Both entities can relate to multiple instances of each other

### 🔹 Use Case:

* Student ↔ Coaching
  (A student joins multiple coachings; a coaching teaches many students)

### 🔹 Setup:

#### 🔸 Owning Side:

```java
@ManyToMany
@JoinTable(
    name = "student_coaching",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "coaching_id")
)
private Set<Coaching> coachings;
```

#### 🔸 Inverse Side:

```java
@ManyToMany(mappedBy = "coachings")
private Set<Student> students;
```

* `@JoinTable` defines the **join table**
* `joinColumns` = FK of current (owning) entity
* `inverseJoinColumns` = FK of related entity

---

## ✅ Foreign Key Rules Summary

| Side/Entity          | Holds FK? | Needs `@JoinColumn`? | Needs `mappedBy`? |
| -------------------- | --------- | -------------------- | ----------------- |
| `@ManyToOne`         | ✅ Yes     | ✅ Yes                | ❌ No              |
| `@OneToOne` (owning) | ✅ Yes     | ✅ Yes                | ❌ No              |
| `@OneToOne` (inv.)   | ❌ No      | ❌ No                 | ✅ Yes             |
| `@OneToMany` (inv.)  | ❌ No      | ❌ No                 | ✅ Yes             |
| `@OneToMany` (uni.)  | ✅ Yes     | ✅ Yes                | ❌ No              |
| `@ManyToMany`        | ↔ Shared  | ✅ (on owning only)   | ✅ (on inverse)    |

---

## ✅ Tips & Best Practices

* Always put `@JoinColumn` on the **owning side** (FK holder).
* `mappedBy` always refers to the **field name**, not table/column.
* For unidirectional relationships:

  * `@ManyToOne` is simpler and FK is managed easily.
  * Avoid unidirectional `@OneToMany` unless using `@JoinColumn`.
* Use `cascade = CascadeType.ALL` + `orphanRemoval = true` to persist/delete child entities automatically.
* Prefer `Set<>` for `@ManyToMany` to avoid duplicates.
* Use `@JsonIgnore` or `@JsonManagedReference` / `@JsonBackReference` (or Gson's `@Expose`) to prevent infinite recursion during JSON serialization.

---

Let me know if you want this as a PDF or formatted doc for future reference!
