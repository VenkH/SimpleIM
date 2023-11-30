package session;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Session {
    /**
     * 用户唯一标识
     */
    private String userId;
    private String userName;
}
