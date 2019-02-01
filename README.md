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
            of this methods as finding field in 
            `getDeclaredFields()` by name or throwing 
            `NoSuchFieldException` (if field cannot be found)
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
        
        assertThat(fields.length, is(4));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ChildInterface.FIELD"));
        ```
    * parent
        ```
        var fields = Parent.class.getFields();
        
        assertThat(fields.length, is(2));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
        ```
* `getDeclaredFields`
    * child
        ```
        var fields = Child.class.getDeclaredFields();
        
        assertThat(fields.length, is(4));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Child.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Child.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Child.protectedField"));
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
        ```
    * parent
        ```
        var fields = Parent.class.getDeclaredFields();
        
        assertThat(fields.length, is(4));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Parent.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Parent.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Parent.protectedField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        ```
* get field by name
    * private field - exception
        ```
        @Test(expected = NoSuchFieldException.class)
        public void getField_notPublic() throws NoSuchFieldException {
            Child.class.getField("privateField");
        }
        ```
    * field that does not exist
        ```
        @Test(expected = NoSuchFieldException.class)
        public void getField_notExists() throws NoSuchFieldException {
            Child.class.getField("not exists");
        }
        ```
    * public field
        ```
        @Test
        public void getField_public() throws NoSuchFieldException {
            assertThat(Child.class.getField("publicField").toGenericString(), is("public int Child.publicField"));
        }
        ```