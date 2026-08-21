package com.API_de_Enderecos.specification;

import com.API_de_Enderecos.entity.Endereco;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class EnderecoSpecification {

    public static Specification<Endereco> rua(String rua) {
        return (root, query, cb) -> 
            Objects.nonNull(rua) && !rua.isBlank() 
                ? cb.like(cb.lower(root.get("rua")), "%" + rua.toLowerCase() + "%")
                : cb.conjunction();
    }

    public static Specification<Endereco> numero(Integer numero) {
        return (root, query, cb) -> 
            Objects.nonNull(numero) 
                ? cb.equal(root.get("numero"), numero)
                : cb.conjunction();
    }

    public static Specification<Endereco> complemento(String complemento) {
        return (root, query, cb) -> 
            Objects.nonNull(complemento) && !complemento.isBlank()
                ? cb.like(cb.lower(root.get("complemento")), "%" + complemento.toLowerCase() + "%")
                : cb.conjunction();
    }

    public static Specification<Endereco> bairro(String bairro) {
        return (root, query, cb) -> 
            Objects.nonNull(bairro) && !bairro.isBlank()
                ? cb.like(cb.lower(root.get("bairro")), "%" + bairro.toLowerCase() + "%")
                : cb.conjunction();
    }

    public static Specification<Endereco> cidade(String cidade) {
        return (root, query, cb) -> 
            Objects.nonNull(cidade) && !cidade.isBlank()
                ? cb.like(cb.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%")
                : cb.conjunction();
    }

    public static Specification<Endereco> estado(String estado) {
        return (root, query, cb) -> 
            Objects.nonNull(estado) && !estado.isBlank()
                ? cb.equal(cb.upper(root.get("estado")), estado.toUpperCase())
                : cb.conjunction();
    }

    public static Specification<Endereco> cep(String cep) {
        return (root, query, cb) -> 
            Objects.nonNull(cep) && !cep.isBlank()
                ? cb.like(root.get("cep"), "%" + cep + "%")
                : cb.conjunction();
    }

    public static Specification<Endereco> clienteId(Long clienteId) {
        return (root, query, cb) -> 
            Objects.nonNull(clienteId) 
                ? cb.equal(root.get("cliente").get("id"), clienteId)
                : cb.conjunction();
    }
}
