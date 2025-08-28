package xyz.yuanowo.keelungsightsviewer.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import xyz.yuanowo.keelungsightsviewer.model.ErrorMessage;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 處理 @Valid 驗證失敗（RequestBody）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // HTTP 422
    public ErrorMessage handleValidationException(MethodArgumentNotValidException ex) {
        return new ErrorMessage("422", ex.getMessage());
    }

    // 處理 @Validated 驗證失敗（RequestParam, PathVariable）
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ErrorMessage handleConstraintViolation(ConstraintViolationException ex) {
        return new ErrorMessage("422", ex.getMessage());
    }

    // 處理參數型別不匹配錯誤
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ErrorMessage handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();   // 參數名稱
        String paramValue = String.valueOf(ex.getValue()); // 錯誤值
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "未知型別";
        String msg = String.format("%s 應為 %s 型別，但收到的值為 '%s'", paramName, requiredType, paramValue);

        return new ErrorMessage("422", msg);
    }

    // 處理未知的路徑請求
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorMessage handleNoHandlerFound(NoHandlerFoundException ex) {
        return new ErrorMessage("404", "Unknown path: " + ex.getRequestURL());
    }

}
