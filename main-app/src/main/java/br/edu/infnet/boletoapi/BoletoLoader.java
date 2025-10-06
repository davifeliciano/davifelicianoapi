package br.edu.infnet.boletoapi;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.infnet.boletoapi.model.domain.Boleto;
import br.edu.infnet.boletoapi.model.domain.Endereco;
import br.edu.infnet.boletoapi.model.domain.Pessoa;
import br.edu.infnet.boletoapi.service.BoletoService;

@Component
public class BoletoLoader implements ApplicationRunner {
    private final BoletoService boletoService;

    public BoletoLoader(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader file = new FileReader("boletos.csv");
        BufferedReader reader = new BufferedReader(file);

        reader.readLine();
        String line = reader.readLine();

        while (line != null) {
            String[] campos = line.split(",");
            Boleto boleto = new Boleto();
            Pessoa cedente = new Pessoa();
            Pessoa sacado = new Pessoa();
            Endereco enderecoCedente = new Endereco();
            Endereco enderecoSacado = new Endereco();

            cedente.setEndereco(enderecoCedente);
            sacado.setEndereco(enderecoSacado);
            boleto.setCedente(cedente);
            boleto.setSacado(sacado);

            boleto.setCodigoDeBarras(campos[0]);
            boleto.setNossoNumero(campos[1]);
            boleto.setDataVencimento(campos[2]);
            boleto.setValor(Double.parseDouble(campos[3]));
            boleto.setPago(Boolean.parseBoolean(campos[4]));
            boleto.setDescricao(campos[5]);

            cedente.setNomeRazaoSocial(campos[7]);
            cedente.setCpfCnpj(campos[8]);

            enderecoCedente.setLogradouro(campos[9]);
            enderecoCedente.setNumero(campos[10]);
            enderecoCedente.setComplemento(campos[11]);
            enderecoCedente.setBairro(campos[12]);
            enderecoCedente.setCidade(campos[13]);
            enderecoCedente.setEstado(campos[14]);
            enderecoCedente.setCep(campos[15]);

            sacado.setNomeRazaoSocial(campos[17]);
            sacado.setCpfCnpj(campos[18]);

            enderecoSacado.setLogradouro(campos[19]);
            enderecoSacado.setNumero(campos[20]);
            enderecoSacado.setComplemento(campos[21]);
            enderecoSacado.setBairro(campos[22]);
            enderecoSacado.setCidade(campos[23]);
            enderecoSacado.setEstado(campos[24]);
            enderecoSacado.setCep(campos[25]);

            boletoService.incluir(boleto);
            System.out.println(boleto);
            line = reader.readLine();
        }

        reader.close();
        file.close();
    }
}
