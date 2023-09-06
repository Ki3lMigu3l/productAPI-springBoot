package io.github.ki3lmigu3l.produtos.controllers;

import io.github.ki3lmigu3l.produtos.dtos.ProdutoRecordDTO;
import io.github.ki3lmigu3l.produtos.model.ProdutoModel;
import io.github.ki3lmigu3l.produtos.repositories.ProdutoRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProdutoController {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoModel> salvarProduto(@RequestBody @Valid ProdutoRecordDTO produtoRecordDTO){
        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoRecordDTO, produtoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepositorio.save(produtoModel));
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoModel>> obterProduto(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepositorio.findAll());
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> obterProdutoById(@PathVariable(value = "id") UUID id){
        Optional<ProdutoModel> produtoModelOptional = produtoRepositorio.findById(id);
        if(produtoModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoModelOptional.get());
    }
}
