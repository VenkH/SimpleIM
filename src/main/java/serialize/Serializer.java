package serialize;

import serialize.impl.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 返回序列化算法
     * @return 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     * @param object 序列化的对象
     * @return 序列化后的字节数组
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     * @param bytes 字节数组
     * @param clazz 对象类型
     * @return 反序列化的对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
