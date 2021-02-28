package br.com.igorcarvalho.pdfgen.model.entity;

import br.com.igorcarvalho.pdfgen.model.abstracts.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "multas")
public class MultaEntity extends AbstractEntity {
    private String atividade;
    private String autoInfracao;
    private String codigoProtocolo;
}
