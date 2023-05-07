# excel


# mariadb (merge)

```sql
INSERT INTO table_name1
(컬럼명1, 컬럼명2, 컬럼명3.. )
SELECT 컬럼명1
, 컬럼명2
, 컬럼명3
...
FROM table_name2
ON DUPLICATE KEY
UPDATE 수정할 컬럼명1 = 수정될 새로운 값1
, 수정할 컬럼명2 = 수정될 새로운 값2
```