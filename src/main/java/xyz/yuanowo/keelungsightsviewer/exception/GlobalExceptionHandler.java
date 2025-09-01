package xyz.yuanowo.keelungsightsviewer.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import xyz.yuanowo.keelungsightsviewer.model.ResponseMessage;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class, // @Valid 驗證失敗 (RequestBody)
                        ConstraintViolationException.class })  // @Validated 驗證失敗 (RequestParam, PathVariable)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseMessage handle422Exception(Exception ex) {
        return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // 參數型別不匹配錯誤
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseMessage handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY,
                                   String.format("參數 '%s' 的值 '%s' 無法轉換為 '%s' 類型", ex.getName(),
                                                 ex.getValue(),
                                                 ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName()
                                                                              : "未知"));
    }

    @ExceptionHandler({ NoHandlerFoundException.class,  // 找不到路徑的錯誤
                        NoResourceFoundException.class, // 靜態資源找不到的錯誤
                        NotFoundException.class })      // 自訂的 NotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handle404Exception(Exception ex) {
        return new ResponseMessage(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 處理未知例外
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ResponseMessage handleGenericException(Exception ex) {
        ex.printStackTrace(); // 印出錯誤堆疊追蹤以便除錯
        return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}
