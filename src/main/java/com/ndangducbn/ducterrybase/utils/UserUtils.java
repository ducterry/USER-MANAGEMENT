package com.ndangducbn.ducterrybase.utils;

import com.ndangducbn.ducterrybase.common.constant.varconstanst.VariableConstants;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

@Slf4j
public class UserUtils {
    public static String PREFIX = "UserUtils_";


    public static String hashPassword(String password) {
        log.debug(PREFIX + "hashPassword => {}", JSONFactory.toString(password));

        return BCrypt.hashpw(password, BCrypt.gensalt(VariableConstants.LOG_ROUNDS));
    }
}
