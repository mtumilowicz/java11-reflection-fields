# java11-reflection-fields
Fields descriptors obtained via reflection.

# preface
https://github.com/mtumilowicz/java-reflection

* `java.lang.reflect.Field` is used to represent field
* `Class` provides us with four methods to gather
fields information:
    * all fields:
        * `Field[] getFields()` - returns an array 
            containing `Field` objects reflecting all
            the accessible public fields of the class or 
            interface represented by Class object.
            
            If this `Class` object represents a class, then 
            this method returns the public fields of the 
            class and of all its superclasses and
            superinterfaces.
            
            If this `Class` object represents an interface, 
            then this method returns the fields of the interface 
            and of all its superinterfaces.
        * `Field[] getDeclaredFields()` - returns 
            an array of `Field` objects reflecting all the fields
            declared by the class or interface represented by this
            `Class` object. This includes public, protected, default
            (package) access, and private fields, but excludes 
            inherited fields.
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
# project description