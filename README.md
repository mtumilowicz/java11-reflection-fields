[![Build Status](https://travis-ci.com/mtumilowicz/java11-reflection-fields.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-reflection-fields)

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
            all fields explicitly declared by the class or interface 
            (not inherited)
    * if we know field's name:
        * `Field getField(String name)` - you can think
            of this methods as a way of trying to find a field in `getFields()`
            by its name or throwing `NoSuchFieldException` (if the field
            cannot be found)
            
            algorithm (field could be shadowed):
            
            Let `C` be the class or interface represented by this object:
            1. If `C` declares a public field with the name specified, that is the
            field to be reflected.
            1. If no field was found in step `a.` above, this algorithm is applied
            recursively to each direct superinterface of `C`. The direct
            superinterfaces are searched in the order they were declared.
            1. If no field was found in steps `a.` and `b.` above, and `C` has a
            superclass `S`, then this algorithm is invoked recursively upon `S`.
            1. If `C` has no superclass, then a `NoSuchFieldException`
            is thrown.
            
        * `Field getDeclaredField(String name)` - you can think
            of this methods as a way of trying to find a field in 
            `getDeclaredFields()` by its name or throwing 
            `NoSuchFieldException` (if the field cannot be found)
# project description
We will show how to obtain info about declared fields.

Class structure is as simple as it can be:
* parent
    ```
    class Parent implements ParentInterface {
        private int privateField;
        String packagePrivateField;
        protected Object protectedField;
        public int publicField;
        public String publicParentField;
    }
    
    interface ParentInterface {
        String FIELD = "";
    }
    ```
* child
    ```
    class Child extends Parent implements ChildInterface {
        
        private int privateField;
        String packagePrivateField;
        protected Object protectedField;
        public int publicField;
        
    }
    
    interface ChildInterface {
        String FIELD = "";
    }
    ```

All tests are in `FieldReflection` class
* `getFields`
    * child
        ```
        var fields = Child.class.getFields();

        assertThat(fields.length, is(5));

        var fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public java.lang.String Parent.publicParentField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ChildInterface.FIELD"));
        ```
    * parent
        ```
        var fields = Parent.class.getFields();

        assertThat(fields.length, is(3));

        var fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public java.lang.String Parent.publicParentField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
        ```
* `getDeclaredFields`
    * child
        ```
        var fields = Child.class.getDeclaredFields();
        
        assertThat(fields.length, is(4));
        
        var fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Child.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Child.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Child.protectedField"));
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
        ```
    * parent
        ```
        var fields = Parent.class.getDeclaredFields();

        assertThat(fields.length, is(5));

        var fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Parent.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Parent.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Parent.protectedField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public java.lang.String Parent.publicParentField"));
        ```
* get field by name
    * private field - `NoSuchFieldException`
        ```
        @Test(expected = NoSuchFieldException.class)
        public void getField_notPublic() throws NoSuchFieldException {
            Child.class.getField("privateField");
        }
        ```
    * field that does not exist - `NoSuchFieldException`
        ```
        @Test(expected = NoSuchFieldException.class)
        public void getField_notExists() throws NoSuchFieldException {
            Child.class.getField("not exists");
        }
        ```
    * public field (shadowed)
        ```
        assertThat(Child.class.getField("publicField").toGenericString(), 
                is("public int Child.publicField"));
        ```
    * from parent
        ```
        assertThat(Child.class.getField("publicParentField").toGenericString(), 
                is("public java.lang.String Parent.publicParentField"));
        ```
* get declared field by name
    * private field
        ```
        assertThat(Child.class.getDeclaredField("privateField").toGenericString(), 
                is("private int Child.privateField"));
        ```
    * field that does not exist - `NoSuchFieldException`
        ```
        @Test(expected = NoSuchFieldException.class)
        public void getDeclaredField_notExists() throws NoSuchFieldException {
            Child.class.getDeclaredField("not exists");
        }
        ```
    * public field (shadowed)
        ```
        assertThat(Child.class.getDeclaredField("publicField").toGenericString(),
                is("public int Child.publicField"));
        ```
    * from parent - `NoSuchFieldException`
        ```
        @Test(expected = NoSuchFieldException.class)
        public void getDeclaredField_public_fromParent() throws NoSuchFieldException {
            Child.class.getDeclaredField("publicParentField");
        }
        ```