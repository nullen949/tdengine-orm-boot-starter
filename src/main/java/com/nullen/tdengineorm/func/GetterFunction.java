package com.nullen.tdengineorm.func;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Nullen
 */
@FunctionalInterface
public interface GetterFunction<T, R> extends Function<T, R>, Serializable {

}
