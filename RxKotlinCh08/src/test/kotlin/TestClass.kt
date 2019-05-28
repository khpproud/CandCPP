import org.junit.Test
import kotlin.test.assertEquals

class TestClass {
    @Test
    fun `my first test`() {
        assertEquals(3, 1 + 2, "Actual value is not equal to the expected one.")
    }

    @Test
    fun `addition test`() = assertEquals(1 + 2, add(1, 2))
    @Test
    fun `subtraction test`() = assertEquals(8 - 5, subtract(8, 5))
    @Test
    fun `multiplication test`() = assertEquals(4 * 2, multiply(4, 2))
    @Test
    fun `division test`() = assertEquals(8 / 2, divide(8, 2))
}