package com.hexagon.boot.libs.util;


import com.hexagon.boot.libs.UnitTestError;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AssertsTest {

    @Test
    public void isTrue() {
        try {
            Asserts.isTrue(false, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        Asserts.isTrue(true, "test error");
    }

    @Test
    public void isTrue1() {
        Asserts.isTrue(true, ()->"test error");
        try {
            Asserts.isTrue(false, () ->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }
    
    @Test
    public void state() {
        Asserts.state(true, "test error");
        try {
            Asserts.state(false, "test error");
            throw new UnitTestError();
        } catch (IllegalStateException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        
    }

    @Test
    public void state1() {
        Asserts.state(true, ()-> "test error");
        try {
            Asserts.state(false, ()-> "test error");
            throw new UnitTestError();
        } catch (IllegalStateException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void isNull() {
        Asserts.isNull(null, "test error");
        try {
            Asserts.isNull(new Object(), "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void isNull1() {
        Asserts.isNull(null, ()-> "test error");
        try {
            Asserts.isNull(new Object(), ()-> "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notNull() {
        Asserts.notNull(new Object(), "test error");
        try {
            Asserts.notNull(null, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notNull1() {
        Asserts.notNull(new Object(), ()->"test error");
        try {
            Asserts.notNull(null, ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void hasLength() {
        Asserts.hasLength("aaaa", "test error");
        try {
            Asserts.hasLength(null, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.hasLength("", "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void hasLength1() {
        Asserts.hasLength("aaaa", ()->"test error");
        try {
            Asserts.hasLength(null, ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.hasLength("", ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void hasText() {
        Asserts.hasText("1111a", "test error");
        try {
            Asserts.hasText(null, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.hasText("", "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.hasText("        ", "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void hasText1() {
        Asserts.hasText("1111a", ()->"test error");
        try {
            Asserts.hasText(null, ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.hasText("", ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.hasText("        ", ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void doesNotContain() {
        Asserts.doesNotContain("1111a","", "test error");
        Asserts.doesNotContain(null,null, "test error");
        Asserts.doesNotContain("","", "test error");
        Asserts.doesNotContain("        ","", "test error");
        try {
            Asserts.doesNotContain("aaba","b", "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.doesNotContain("        "," ", "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.doesNotContain(" adfsdfasf"," ", "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void doesNotContain1() {
        Asserts.doesNotContain("1111a","", ()->"test error");
        Asserts.doesNotContain(null,null, ()->"test error");
        Asserts.doesNotContain("","", ()->"test error");
        Asserts.doesNotContain("        ","", ()->"test error");
        try {
            Asserts.doesNotContain("aaba","b", ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.doesNotContain("        "," ", ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
        try {
            Asserts.doesNotContain(" adfsdfasf"," ", ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notEmpty() {
        Asserts.notEmpty(new String[]{null}, "test error");
        Asserts.notEmpty(new String[]{""}, "test error");
        Asserts.notEmpty(new String[]{"1111"}, "test error");
        try {
            Asserts.notEmpty(new String[]{}, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notEmpty1() {
        Asserts.notEmpty(new String[]{null}, ()->"test error");
        Asserts.notEmpty(new String[]{""}, ()->"test error");
        Asserts.notEmpty(new String[]{"1111"}, ()->"test error");
        try {
            Asserts.notEmpty(new String[]{}, ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notEmpty2() {
        Collection collection = new HashSet();
        collection.add(1);
        collection.add(2);
        Asserts.notEmpty(collection, "test error");
        try {

            Asserts.notEmpty(new HashSet<>(), "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }

        try {

            Asserts.notEmpty(new HashSet<>(10), "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notEmpty3() {
        Collection collection = new ArrayList();
        collection.add(1);
        collection.add(2);
        Asserts.notEmpty(collection, ()->"test error");
        try {

            Asserts.notEmpty(new ArrayList<>(), ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }

        try {

            Asserts.notEmpty(new ArrayList<>(10), ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notEmpty4() {
        Map map = new HashMap();
        map.put("11", 1);
        map.put("22", 2);
        Asserts.notEmpty(map, "test error");
        try {

            Asserts.notEmpty(new HashMap<>(), "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }

        try {

            Asserts.notEmpty(new HashMap<>(10), "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void notEmpty5() {
        Map map = new HashMap();
        map.put("11", 1);
        map.put("22", 2);
        Asserts.notEmpty(map, ()->"test error");
        try {

            Asserts.notEmpty(new HashMap<>(), ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }

        try {

            Asserts.notEmpty(new HashMap<>(10), ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }


    @Test
    public void noNullElements() {

        Asserts.noNullElements(new String[]{}, "test error");
        Asserts.noNullElements(new String[]{""}, "test error");
        Asserts.noNullElements(new String[]{"1","@","3"}, "test error");
        try {
            Asserts.noNullElements(new String[]{null}, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }

        try {

            Asserts.noNullElements(new String[]{"222",null}, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    @Test
    public void noNullElements1() {
        Asserts.noNullElements(new String[]{}, ()->"test error");
        Asserts.noNullElements(new String[]{""}, ()->"test error");
        Asserts.noNullElements(new String[]{"1","@","3"}, ()->"test error");
        try {
            Asserts.noNullElements(new String[]{null}, ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }

        try {

            Asserts.noNullElements(new String[]{"222",null}, ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error", e.getMessage());
        }
    }

    interface Interface{}
    class Parent {}
    class Child extends Parent implements Interface {}

    @Test
    public void isInstanceOf() {
        Asserts.isInstanceOf(Object.class, new ArrayList<>());
        Asserts.isInstanceOf(Parent.class, new Parent());
        Asserts.isInstanceOf(Parent.class, new Child());
        Asserts.isInstanceOf(Interface.class, new Child());

        try {
            Asserts.isInstanceOf(Interface.class, new Parent());
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Object of class [com.changyi.libs.common.util.AssertsTest$Parent] must be an instance of interface com.changyi.libs.common.util.AssertsTest$Interface",
                    e.getMessage());
        }

    }

    @Test
    public void isInstanceOf1() {
        Asserts.isInstanceOf(Object.class, new ArrayList<>(), "test error");
        Asserts.isInstanceOf(Parent.class, new Parent(),"test error");
        Asserts.isInstanceOf(Parent.class, new Child(),"test error");
        Asserts.isInstanceOf(Interface.class, new Child(),"test error");

        try {
            Asserts.isInstanceOf(Interface.class, new Parent(), "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error: com.changyi.libs.common.util.AssertsTest$Parent", e.getMessage());
        }
    }

    @Test
    public void isInstanceOf2() {
        Asserts.isInstanceOf(Object.class, new ArrayList<>(), ()->"test error");
        Asserts.isInstanceOf(Parent.class, new Parent(),()->"test error");
        Asserts.isInstanceOf(Parent.class, new Child(),()->"test error");
        Asserts.isInstanceOf(Interface.class, new Child(),()->"test error");

        try {
            Asserts.isInstanceOf(Interface.class, new Parent(), ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error: com.changyi.libs.common.util.AssertsTest$Parent", e.getMessage());
        }
    }

    @Test
    public void isAssignable() {
        Asserts.isAssignable(Object.class, ArrayList.class);
        Asserts.isAssignable(Parent.class, Parent.class);
        Asserts.isAssignable(Parent.class, Child.class);
        Asserts.isAssignable(Interface.class, Child.class);

        try {
            Asserts.isAssignable(Interface.class, Parent.class);
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("class com.changyi.libs.common.util.AssertsTest$Parent is not assignable to interface com.changyi.libs.common.util.AssertsTest$Interface",
                    e.getMessage());
        }
    }

    @Test
    public void isAssignable1() {
        Asserts.isAssignable(Object.class, ArrayList.class, "test error");
        Asserts.isAssignable(Object.class, ArrayList.class, "test error");
        Asserts.isAssignable(Parent.class, Parent.class, "test error");
        Asserts.isAssignable(Parent.class, Child.class, "test error");
        Asserts.isAssignable(Interface.class, Child.class, "test error");

        try {
            Asserts.isAssignable(Interface.class, Parent.class, "test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error: class com.changyi.libs.common.util.AssertsTest$Parent",
                    e.getMessage());
        }
    }

    @Test
    public void isAssignable2() {
        Asserts.isAssignable(Object.class, ArrayList.class, ()->"test error");
        Asserts.isAssignable(Parent.class, Parent.class, ()->"test error");
        Asserts.isAssignable(Parent.class, Child.class, ()->"test error");
        Asserts.isAssignable(Interface.class, Child.class, ()->"test error");

        try {
            Asserts.isAssignable(Interface.class, Parent.class, ()->"test error");
            throw new UnitTestError();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("test error: class com.changyi.libs.common.util.AssertsTest$Parent",
                    e.getMessage());
        }
    }
}
