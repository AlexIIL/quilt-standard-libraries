/*
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qsl.registry.attachment.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import org.jetbrains.annotations.Nullable;

public final class CodecUtils {
	private CodecUtils() { }

	public static <T> void assertValid(Codec<T> codec, T value) {

		String actualError = getErrorIfInvalid(codec, value);

		if (actualError == null) {
			return;
		}

		throw new IllegalArgumentException("Value is invalid: " + actualError);
	}

	@Nullable
	public static <T> String getErrorIfInvalid(Codec<T> codec, T value) {
		var encoded = codec.encodeStart(JsonOps.INSTANCE, value);

		if (encoded.result().isEmpty()) {
			if (encoded.error().isPresent()) {
				return encoded.error().get().message();
			} else {
				return "unknown error";
			}
		}

		return null;
	}
}
