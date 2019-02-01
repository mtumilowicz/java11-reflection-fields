# java11-reflection-fields
Fields descriptors obtained via reflection.

# preface
https://github.com/mtumilowicz/java-reflection

* `java.lang.reflect.Field` is used to represent field
* `Class` provides us with four methods to gather
fields information:
    * all fields:
        * `Field[] getFields()` - returns all public fields 
            of the class or interface (including public fields
            from superclasses and superinterfaces)
        * `Field[] getDeclaredFields()` - returns 
            all fields declared by the class or interface (
            excluding inherited fields)
    * if we know field's name:
        * `Field getField(String name)` - you can think
            of this methods as finding field in `getFields()`
            by name or throwing `NoSuchFieldException` (if field
            cannot be found)
        * `Field getDeclaredField(String name)` - you can think
            of this methods as finding field in 
            `getDeclaredFields()` by name or throwing 
            `NoSuchFieldException` (if field cannot be found)
# project description
We will show how to obtain info about declared fields.