import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargaDocumentosCompraComponent } from './carga-documentos-compra.component';

describe('CargaDocumentosCompraComponent', () => {
  let component: CargaDocumentosCompraComponent;
  let fixture: ComponentFixture<CargaDocumentosCompraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CargaDocumentosCompraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CargaDocumentosCompraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
