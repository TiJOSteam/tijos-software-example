
/**
 * java 移位操作例程
 * JAVA中对byte进行移位运算时必须小心，JAVA在对表达式求值时会自动将byte类型
 * 扩大为int类型，因此在左移时需要将结果转换为byte型，右移时需要与0x0f相与才能获得正确结果
 *
 * @author TiJOS
 */
public class HexByte {
    static public void main(String args[]) {
        char[] hex = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        byte b = (byte) 0xf1;
        System.out.println("b=0x" + hex[(b >> 4) & 0x0f] + hex[b & 0x0f]);
    }
}
