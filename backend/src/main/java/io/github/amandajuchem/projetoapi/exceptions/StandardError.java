package io.github.amandajuchem.projetoapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Standard error.
 *
 * @author Edson Isaac
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}