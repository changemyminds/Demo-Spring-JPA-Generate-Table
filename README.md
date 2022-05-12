## Demo Sorting Generate table in Spring Data JPA.

If you want to use class field to determine the column order of the table, you need fix the `PropertyContainer.java` and `InheritanceState.java`.

### Demo1
```java
@Entity
public class Organization {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String organizationNumber;

    @Column(nullable = false)
    private String name;
    
    // ignore getter setter
}
```

The console result
```
Before fix.
Hibernate: create table organization (id bigint not null, name varchar(255) not null, organization_number varchar(255) not null, primary key (id)) engine=InnoDB

After fix.
Hibernate: create table organization (id bigint not null, organization_number varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB
```

The Database result
```
Before fix.
mysql> desc organization;
+---------------------+--------------+------+-----+---------+-------+
| Field               | Type         | Null | Key | Default | Extra |
+---------------------+--------------+------+-----+---------+-------+
| id                  | bigint(20)   | NO   | PRI | NULL    |       |
| name                | varchar(255) | NO   |     | NULL    |       |
| organization_number | varchar(255) | NO   | UNI | NULL    |       |
+---------------------+--------------+------+-----+---------+-------+

After fix.
mysql> desc organization;
+---------------------+--------------+------+-----+---------+-------+
| Field               | Type         | Null | Key | Default | Extra |
+---------------------+--------------+------+-----+---------+-------+
| id                  | bigint(20)   | NO   | PRI | NULL    |       |
| organization_number | varchar(255) | NO   | UNI | NULL    |       |
| name                | varchar(255) | NO   |     | NULL    |       |
+---------------------+--------------+------+-----+---------+-------+
```

### Demo2
```java
@Entity
public class OrganizationAudit extends AuditableEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String organizationNumber;

    @Column(nullable = false)
    private String name;
    
    // ignore getter setter
}

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "update_time")
    private ZonedDateTime updatedTime;

    @CreatedDate
    @Column(name = "create_time")
    private ZonedDateTime createTime;

    // ignore getter setter
}
```

The console result
```
Before fix.
Hibernate: create table organization_audit (id bigint not null, create_time datetime, created_by varchar(255), last_modified_by varchar(255), update_time datetime, name varchar(255) not null, organization_number varchar(255) not null, primary key (id)) engine=InnoDB

After fix.
Hibernate: create table organization (id bigint not null, organization_number varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB
```

The Database result
```
Before fix.
mysql> desc organization_audit;
+---------------------+--------------+------+-----+---------+-------+
| Field               | Type         | Null | Key | Default | Extra |
+---------------------+--------------+------+-----+---------+-------+
| id                  | bigint(20)   | NO   | PRI | NULL    |       |
| create_time         | datetime     | YES  |     | NULL    |       |
| created_by          | varchar(255) | YES  |     | NULL    |       |
| last_modified_by    | varchar(255) | YES  |     | NULL    |       |
| update_time         | datetime     | YES  |     | NULL    |       |
| name                | varchar(255) | NO   |     | NULL    |       |
| organization_number | varchar(255) | NO   | UNI | NULL    |       |
+---------------------+--------------+------+-----+---------+-------+

After fix.
mysql> desc organization_audit;
+---------------------+--------------+------+-----+---------+-------+
| Field               | Type         | Null | Key | Default | Extra |
+---------------------+--------------+------+-----+---------+-------+
| id                  | bigint(20)   | NO   | PRI | NULL    |       |
| organization_number | varchar(255) | NO   | UNI | NULL    |       |
| name                | varchar(255) | NO   |     | NULL    |       |
| created_by          | varchar(255) | YES  |     | NULL    |       |
| last_modified_by    | varchar(255) | YES  |     | NULL    |       |
| update_time         | datetime     | YES  |     | NULL    |       |
| create_time         | datetime     | YES  |     | NULL    |       |
+---------------------+--------------+------+-----+---------+-------+
```