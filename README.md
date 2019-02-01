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
        * `Field getField(String name)` - Returns a `Field` 
            object that reflects the specified public member
            field of the class or interface represented by this 
            `Class` object.
        
            Let C be the class or interface:
            1. If C declares a public field with the name specified, that is the
                field to be reflected.
            1. If no field was found in step 1 above, this algorithm is applied
                recursively to each direct superinterface of C. The direct
                superinterfaces are searched in the order they were declared.
            1. If no field was found in steps 1 and 2 above, and C has a
                superclass S, then this algorithm is invoked recursively upon S.
            1. If C has no superclass, then a `NoSuchFieldException`
                is thrown.
        * `Field getDeclaredField(String name)` - returns 
            a `Field` object that reflects the specified 
            declared field of the class or interface represented 
            by this `Class` object.
            
            `NoSuchFieldException` if a field with the specified 
            name is not found.
            
            `SecurityException` if a security manager, <i>s</i>, 
            is present and some conditions are met (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Class.html).
# project description