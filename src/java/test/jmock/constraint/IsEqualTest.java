package test.jmock.constraint;

import junit.framework.TestCase;

import org.jmock.Constraint;
import org.jmock.constraint.IsEqual;
import org.jmock.dynamock.Mock;
import org.jmock.expectation.AssertMo;

import test.jmock.dynamic.DummyInterface;


public class IsEqualTest extends TestCase {
    public void testComparesObjectsUsingEqualsMethod() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Constraint c = new IsEqual(i1);

        assertTrue(c.eval(i1));
        assertTrue(c.eval(new Integer(1)));
        assertTrue(!c.eval(i2));
    }

    public void testCanCompareNullValues() {
        Integer i1 = new Integer(1);
        Constraint c = new IsEqual(i1);
        
        assertTrue(!c.eval(null));
        Constraint nullEquals = new IsEqual(null);
        assertTrue(nullEquals.eval(null));
        assertTrue(!nullEquals.eval(i1));
    }

    public void testComparesTheElementsOfAnObjectArray() {
        String[] s1 = new String[]{"a", "b"};
        String[] s2 = new String[]{"a", "b"};
        String[] s3 = new String[]{"c", "d"};
        String[] s4 = new String[]{"a", "b", "c", "d"};

        Constraint c = new IsEqual(s1);

        assertTrue("Should equal itself", c.eval(s1));
        assertTrue("Should equal a similar array", c.eval(s2));
        assertTrue("Should not equal a different array", !c.eval(s3));
        assertTrue("Should not equal a different sized array", !c.eval(s4));
    }
    
    public void testComparesTheElementsOfAnArrayOfPrimitiveTypes() {
        int[] i1 = new int[]{1, 2};
        int[] i2 = new int[]{1, 2};
        int[] i3 = new int[]{3, 4};
        int[] i4 = new int[]{1, 2, 3, 4};
        
        Constraint c = new IsEqual(i1);

        assertTrue("Should equal itself", c.eval(i1));
        assertTrue("Should equal a similar array", c.eval(i2));
        assertTrue("Should not equal a different array", !c.eval(i3));
        assertTrue("Should not equal a different sized array", !c.eval(i4));
    }
    
    public void testRecursivelyTestsElementsOfArrays() {
        int[][] i1 = new int[][]{{1,2},{3,4}};
        int[][] i2 = new int[][]{{1,2},{3,4}};
        int[][] i3 = new int[][]{{5,6},{7,8}};
        int[] i4 = new int[]{1, 2, 3, 4};
        int[][] i5 = new int[][]{{1,2,3,4},{3,4}};
        
        Constraint c = new IsEqual(i1);

        assertTrue("Should equal itself", c.eval(i1));
        assertTrue("Should equal a similar array", c.eval(i2));
        assertTrue("Should not equal a different array", !c.eval(i3));
        assertTrue("Should not equal a different sized array", !c.eval(i4));
        assertTrue("Should not equal a different sized subarray", !c.eval(i5));
    }
    
    public void testReturnsAnObviousDescriptionIfCreatedWithANestedConstraint() {
        assertEquals("Should get an obvious toString to reflect nesting if viewed in a debugger",
            " =  = NestedConstraint", new IsEqual(new IsEqual("NestedConstraint")).toString());
    }
    
    // TODO: remove this test? This behaviour is already tested by the tests for the CoreMock class.
    public void testReturnsMockNameAsDescriptionIfCreatedWithProxyOfMock() {
        Mock mockDummyInterface = new Mock(DummyInterface.class, "MockName");
        Constraint p = new IsEqual(mockDummyInterface.proxy());

        AssertMo.assertIncludes("should get resolved toString() with no expectation error", 
            "MockName", p.toString());
    }
    
    public void testReturnsGoodDescriptionIfCreatedWithNullReference() {
        assertEquals("Should print toString even if argument is null",
            " = null", new IsEqual(null).toString());
    }
}

