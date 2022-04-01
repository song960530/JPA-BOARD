package practice.jpaboard.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.jpaboard.domain.board.entity.Upload;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadDto {
    private Long no;
    private String originName;
    private String encryptName;

    public UploadDto(Upload upload) {
        this.no = upload.getNo();
        this.originName = upload.getOriginName();
        this.encryptName = upload.getEncryptName();
    }
}
