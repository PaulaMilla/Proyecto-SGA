import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DispersarMedicamentoComponent } from './dispersar-medicamento.component';

describe('DispersarMedicamentoComponent', () => {
  let component: DispersarMedicamentoComponent;
  let fixture: ComponentFixture<DispersarMedicamentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DispersarMedicamentoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DispersarMedicamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
