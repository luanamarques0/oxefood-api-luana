package br.com.ifpe.oxefood_api_luana.util.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.oxefood_api_luana.modelo.acesso.Usuario;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class EntidadeAuditavel extends EntidadeNegocio {

    @JsonIgnore //Serve para não retornar nas consultas essas infos
    @Version //Preenche automaticamente um valor numerico no momento que ocorre uma alteração na entidade(Vc pode ver quantas alterações aconteceram)
    private Long versao;

    @JsonIgnore
    @CreatedDate //Data e criação do registro
    private LocalDate dataCriacao;

    @JsonIgnore
    @LastModifiedDate //Quando foi auterada
    private LocalDate dataUltimaModificacao;

    @CreatedBy
    @ManyToOne
    @JoinColumn
    private Usuario criadoPor; //Guarda o usuario que criou

    @LastModifiedBy
    @ManyToOne
    @JoinColumn
    private Usuario ultimaModificacaoPor; //Guarda o usuario que fez a ultima alteração

}
